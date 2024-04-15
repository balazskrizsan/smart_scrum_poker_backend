package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.poker_modul.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.PokerModuleServiceFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class VoteService_VoteTest extends AbstractIntegrationTest
{
    @Autowired
    private PokerModuleServiceFactory pokerModuleServiceFactory;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_1_insecure_user.sql",
                    "classpath:test/sqls/_preset_insert_1_poker.sql",
                    "classpath:test/sqls/_preset_insert_3_tickets_all_inactive.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @SneakyThrows
    public void successfulVoteCall_savedToDb()
    {
        // Arrange
        Vote testedVote = new VoteFakeBuilder().build();
        Vote expectedVote = new VoteFakeBuilder().id(1L).build();
        InsecureUser expectedInsecureUser = new InsecureUserFakeBuilder().build();

        // Act
        InsecureUser actualInsecureUser = pokerModuleServiceFactory.getVoteService().vote(testedVote);

        // Assert
        Vote actualVote = getDslContext().selectFrom(voteTable).fetchOne().into(Vote.class);

        assertAll(
            () -> assertThat(actualVote).isEqualTo(expectedVote),
            () -> assertThat(actualInsecureUser).isEqualTo(expectedInsecureUser)
        );
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @SneakyThrows
    public void insertWithoutDbUser_ThrowsException()
    {
        // Arrange
        Vote testedVote = new VoteFakeBuilder().build();

        // Act - Assert
        assertThatThrownBy(() -> pokerModuleServiceFactory.getVoteService().vote(testedVote))
            .isInstanceOf(AccountException.class)
            .hasMessage("User not found");
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_1_insecure_user.sql",
                    "classpath:test/sqls/_preset_insert_1_poker.sql",
                    "classpath:test/sqls/_preset_insert_3_tickets_all_inactive.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @SneakyThrows
    public void sendVoteMultipleTimes_fromSecondTheFirstRowWillBeUpdated()
    {
        // Arrange
        Vote testedVote1 = new VoteFakeBuilder().uncertainty((short) 1).complexity((short) 1).effort((short) 1).build();
        Vote testedVote2 = new VoteFakeBuilder().uncertainty((short) 3).complexity((short) 3).effort((short) 3).build();

        Vote expectedVote = new VoteFakeBuilder().id(1L).uncertainty((short) 3).complexity((short) 3).effort((short) 3).calculatedPoint((short) 13).build();

        // Act
        pokerModuleServiceFactory.getVoteService().vote(testedVote1);
        pokerModuleServiceFactory.getVoteService().vote(testedVote2);

        // Assert
        List<Vote> votes = getDslContext().selectFrom(voteTable).fetch().into(Vote.class);
        assertAll(
            () -> assertThat(votes.size()).isEqualTo(1),
            () -> assertThat(votes.getFirst()).isEqualTo(expectedVote)
        );
    }
}
