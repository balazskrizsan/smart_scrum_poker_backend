package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.RoundService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class RoundService_StartTest extends AbstractIntegrationTest
{
    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
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
    public void startAStartableRound_broadcastToRoom()
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
        createInstance(RoundService.class).start(testedPokerIdSecret, testedTicketId);

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
        assertThatThrownBy(() -> createInstance(RoundService.class).start(testedPokerId, testedTicketId))
            .isInstanceOf(PokerException.class)
            .hasMessage(exceptedMessage);
    }
}
