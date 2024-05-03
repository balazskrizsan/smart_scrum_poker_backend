package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1Poker;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3TicketsAllInactive;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert5VotesFor2Poker3Ticket;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.RoundService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteStop;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RoundService_StopTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert1Poker.class, Insert3TicketsAllInactive.class, Insert5VotesFor2Poker3Ticket.class})
    @SneakyThrows
    public void stopAStopableRound_broadcastToRoom()
    {
        // Arrange
        UUID testedPokerIdSecret = PokerFakeBuilder.defaultIdSecure1;
        long testedTicketId = TicketFakeBuilder.defaultId2;
        var expectedTickets = new TicketFakeBuilder().build1to3AsList();
        var expected = new VoteStop(
            Map.of(
                VoteFakeBuilder.defaultCreatedBy3, new VoteFakeBuilder().build3(),
                VoteFakeBuilder.defaultCreatedBy4, new VoteFakeBuilder().build4()
            ),
            9);

        // Act
        VoteStop actual = createInstance(RoundService.class).stop(testedPokerIdSecret, testedTicketId);

        // Assert
        List<Ticket> actualTicket = getDslContext().selectFrom(ticketTable).orderBy(ticketTable.ID.asc()).fetchInto(Ticket.class);

        assertAll(
            () -> assertThat(actual).isEqualTo(expected),
            () -> assertThat(actualTicket).isEqualTo(expectedTickets)
        );
    }

    @Test
    public void notExistingPoker_throwsException()
    {
        // Arrange
        UUID testedPokerId = PokerFakeBuilder.defaultIdSecure1;
        long testedTicketId = TicketFakeBuilder.defaultId1;
        String exceptedMessage = STR."Poker not found: id#\{PokerFakeBuilder.defaultIdSecure1}";

        // Act - Assert
        assertThatThrownBy(() -> createInstance(RoundService.class).stop(testedPokerId, testedTicketId))
            .isInstanceOf(PokerException.class)
            .hasMessage(exceptedMessage);
    }
}
