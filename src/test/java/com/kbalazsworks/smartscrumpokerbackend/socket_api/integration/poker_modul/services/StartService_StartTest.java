package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.poker_modul.services;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.PokerRecord;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.TicketRecord;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.PokerModuleServiceFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class StartService_StartTest extends AbstractIntegrationTest
{
    @Autowired
    private PokerModuleServiceFactory pokerModuleServiceFactory;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @SneakyThrows
    public void insertValidStartData_createsPokerInDb()
    {
        // Arrange
        Poker testedPoker = new PokerFakeBuilder().buildNoId();
        List<Ticket> testedTickets = new TicketFakeBuilder().buildNoIdAsList();

        Poker expectedPoker = new PokerFakeBuilder().id(1L).build();
        Ticket expectedTicket = new TicketFakeBuilder().id(1).pokerId(1).build();

        // Act
        Poker actualPokerResponse = pokerModuleServiceFactory.getStartService().start(testedPoker, testedTickets);

        // Assert
        PokerRecord actualPoker = getDslContext().selectFrom(pokerTable).fetchOne();
        Poker actualPokerRow = actualPoker.into(Poker.class);
        UUID actualPokerIdSecure = actualPoker.getIdSecure();
        actualPoker.setIdSecure(PokerFakeBuilder.defaultIdSecure1);
        Poker actualPokerRowMocked = actualPoker.into(Poker.class);

        Ticket actualTicketRow = getDslContext().selectFrom(ticketTable).fetchOne().into(Ticket.class);

        assertAll(
            () -> assertThat(actualPokerResponse).isEqualTo(actualPokerRow),

            () -> assertTrue(actualPokerIdSecure.toString().matches(UuidPattern)),
            () -> assertThat(actualPokerRowMocked).isEqualTo(expectedPoker),

            () -> assertThat(actualTicketRow).isEqualTo(expectedTicket)
        );
    }
}
