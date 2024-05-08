package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1Poker;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3TicketsAllInactive;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.VoteStartStopService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class VoteStartStopService_StartTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert1Poker.class, Insert3TicketsAllInactive.class})
    @SneakyThrows
    public void startAStartableRound_broadcastToGame()
    {
        // Arrange
        UUID testedPokerIdSecret = PokerFakeBuilder.defaultIdSecure1;
        long testedTicketId = TicketFakeBuilder.defaultId2;
        var expectedTickets = new ArrayList<>()
        {{
            add(new TicketFakeBuilder().build());
            add(new TicketFakeBuilder().isActive2(true).build2());
            add(new TicketFakeBuilder().build3());
        }};

        // Act
        createInstance(VoteStartStopService.class).start(testedPokerIdSecret, testedTicketId);

        // Assert
        List<Ticket> actualTicket = getDslContext().selectFrom(ticketTable).orderBy(ticketTable.ID.asc()).fetch().into(Ticket.class);

        assertThat(actualTicket).isEqualTo(expectedTickets);
    }

    @Test
    public void notExistingPoker_throwsException()
    {
        // Arrange
        UUID testedPokerId = PokerFakeBuilder.defaultIdSecure1;
        long testedTicketId = TicketFakeBuilder.defaultId1;
        String exceptedMessage = STR."Poker not found: id#\{PokerFakeBuilder.defaultIdSecure1}";

        // Act - Assert
        assertThatThrownBy(() -> createInstance(VoteStartStopService.class).start(testedPokerId, testedTicketId))
            .isInstanceOf(PokerException.class)
            .hasMessage(exceptedMessage);
    }
}
