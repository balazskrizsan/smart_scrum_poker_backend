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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

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
    public void insertValidStartData_createsPokerInDb() throws PokerException
    {
        // Arrange
        Poker testedPoker = new PokerFakeBuilder().buildNoId();
        List<Ticket> testedTickets = new TicketFakeBuilder().buildNoIdAsList();

        long expectedPokerId = 1L;
        long expectedTicketId = 1L;
        long expectedTicketPokerId = 1L;
        Poker expectedPoker = new PokerFakeBuilder().buildNoId();
        Ticket expectedTicket = new TicketFakeBuilder().buildNoId();

        // Act
        Poker actualPokerResponse = pokerModuleServiceFactory.getStartService().start(testedPoker, testedTickets);

        // Assert
        PokerRecord actualPoker = getDslContext().selectFrom(pokerTable).fetchOne();
        Poker actualPokerRow = actualPoker.into(Poker.class);

        TicketRecord actualTicket = getDslContext().selectFrom(ticketTable).fetchOne();
        Ticket actualTicketRow = actualTicket.into(Ticket.class);

        assertAll(
            () -> assertThat(actualPokerResponse.id()).isEqualTo(actualPokerRow.id()),
            () -> assertThat(actualPokerResponse.name()).isEqualTo(actualPokerRow.name()),
            () -> assertThat(actualPokerResponse.idSecure()).isEqualTo(actualPokerRow.idSecure()),
            () -> assertThat(actualPokerResponse.createdAt()).isEqualTo(actualPokerRow.createdAt()),
            () -> assertThat(actualPokerResponse.createdBy()).isEqualTo(actualPokerRow.createdBy()),

            () -> assertThat(actualPokerRow.id()).isEqualTo(expectedPokerId),
            () -> assertThat(actualPokerRow.name()).isEqualTo(expectedPoker.name()),
            () -> assertTrue(actualPokerRow.idSecure().toString().matches(UuidPattern)),
            () -> assertThat(actualPokerRow.createdAt()).isEqualTo(expectedPoker.createdAt()),
            () -> assertThat(actualPokerRow.createdBy()).isEqualTo(expectedPoker.createdBy()),

            () -> assertThat(actualTicketRow.id()).isEqualTo(expectedTicketId),
            () -> assertThat(actualTicketRow.pokerId()).isEqualTo(expectedTicketPokerId),
            () -> assertThat(actualTicketRow.name()).isEqualTo(expectedTicket.name()),
            () -> assertThat(actualTicketRow.isActive()).isEqualTo(expectedTicket.isActive())
        );
    }
}
