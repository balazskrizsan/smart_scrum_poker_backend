package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1Poker;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3TicketsAllInactive;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert5VotesFor2Poker3Ticket;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.TicketService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TicketService_DeleteTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {
        Insert3InsecureUser.class,
        Insert1Poker.class,
        Insert3TicketsAllInactive.class,
        Insert5VotesFor2Poker3Ticket.class
    })
    @SneakyThrows
    public void deleteTicketFromFilledDb_deletesTicket()
    {
        // Arrange
        long testedTicketId = TicketFakeBuilder.defaultId1;
        List<Ticket> expectedTickets = List.of(new TicketFakeBuilder().build2(), new TicketFakeBuilder().build3());
        List<Vote> expectedVotes = List.of(
            new VoteFakeBuilder().build3(),
            new VoteFakeBuilder().build4(),
            new VoteFakeBuilder().build5()
        );

        // Act
        createInstance(TicketService.class).delete(testedTicketId);

        // Assert
        List<Ticket> actualTickets = getDslContext().selectFrom(ticketTable).fetchInto(Ticket.class);
        List<Vote> actualVotes = getDslContext().selectFrom(voteTable).fetchInto(Vote.class);

        assertAll(
            () -> assertThat(actualTickets).isEqualTo(expectedTickets),
            () -> assertThat(actualVotes).isEqualTo(expectedVotes)
        );

    }
}
