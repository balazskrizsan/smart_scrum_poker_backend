package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1Poker;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3TicketsAllInactive;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.VoteService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class VoteService_VoteTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert1InsecureUser.class, Insert1Poker.class, Insert3TicketsAllInactive.class})
    @SneakyThrows
    public void successfulVoteCall_savedToDb()
    {
        // Arrange
        Vote testedVote = new VoteFakeBuilder().id(null).build();

        Vote expectedVote = new VoteFakeBuilder().id(1L).build();
        InsecureUser expectedInsecureUser = new InsecureUserFakeBuilder().build();

        // Act
        InsecureUser actualInsecureUser = createInstance(VoteService.class).vote(testedVote);

        // Assert
        Vote actualVote = getDslContext().selectFrom(voteTable).fetchOneInto(Vote.class);

        assertAll(
            () -> assertThat(actualVote).isEqualTo(expectedVote),
            () -> assertThat(actualInsecureUser).isEqualTo(expectedInsecureUser)
        );
    }

    @Test
    @SqlPreset()
    @SneakyThrows
    public void insertWithoutDbUser_ThrowsException()
    {
        // Arrange
        Vote testedVote = new VoteFakeBuilder().id(null).build();

        // Act - Assert
        assertThatThrownBy(() -> createInstance(VoteService.class).vote(testedVote))
            .isInstanceOf(AccountException.class)
            .hasMessage("User not found");
    }

    @Test
    @SqlPreset(presets = {Insert1InsecureUser.class, Insert1Poker.class, Insert3TicketsAllInactive.class})
    @SneakyThrows
    public void sendVoteMultipleTimes_fromSecondTheFirstRowWillBeUpdated()
    {
        // Arrange
        Vote testedVote1 = new VoteFakeBuilder().id(null).uncertainty((short) 1).complexity((short) 1).effort((short) 1).build();
        Vote testedVote2 = new VoteFakeBuilder().id(null).uncertainty((short) 3).complexity((short) 3).effort((short) 3).build();

        Vote expectedVote = new VoteFakeBuilder().id(1L).uncertainty((short) 3).complexity((short) 3).effort((short) 3).calculatedPoint((short) 13).build();

        // Act
        VoteService service = createInstance(VoteService.class);
        service.vote(testedVote1);
        service.vote(testedVote2);

        // Assert
        List<Vote> votes = getDslContext().selectFrom(voteTable).fetch().into(Vote.class);
        assertAll(
            () -> assertThat(votes.size()).isEqualTo(1),
            () -> assertThat(votes.getFirst()).isEqualTo(expectedVote)
        );
    }
}
