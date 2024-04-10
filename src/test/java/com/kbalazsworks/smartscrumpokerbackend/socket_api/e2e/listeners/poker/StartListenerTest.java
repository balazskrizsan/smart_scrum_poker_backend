package com.kbalazsworks.smartscrumpokerbackend.socket_api.e2e.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractE2eTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.stomp.StompSession;
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

public class StartListenerTest extends AbstractE2eTest
{
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_1_insecure_user.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @Test
    @SneakyThrows
    public void startAPoker_createsDbFromStompPublish()
    {
        // Arrange
        StompSession stompSession = getStompSession();
        Poker expectedDbPoker = new PokerFakeBuilder().id(1L).build();
        Ticket expectedDbTicket0 = new TicketFakeBuilder().id(1L).pokerId(expectedDbPoker.id()).build();
        Ticket expectedDbTicket1 = new TicketFakeBuilder().id2(2L).pokerId2(expectedDbPoker.id()).build2();

        // Act
        stompSession.send(
            "/app/poker.start",
            new StartRequest(
                PokerFakeBuilder.defaultSprintName,
                List.of(TicketFakeBuilder.defaultName1, TicketFakeBuilder.defaultName2),
                InsecureUserFakeBuilder.defaultIdSecure1
            ));
        Thread.sleep(1000);

        // Assert
        Poker actualDbPoker = getDslContext().selectFrom(pokerTable).fetchOneInto(Poker.class);
        List<Ticket> actualDbTickets = getDslContext().selectFrom(ticketTable).fetchInto(Ticket.class);
        Ticket actualDbTickets0 = actualDbTickets.get(0);
        Ticket actualDbTickets1 = actualDbTickets.get(1);


        assertAll(
            () -> assertThat(actualDbPoker.id()).isEqualTo(expectedDbPoker.id()),
            () -> assertTrue(actualDbPoker.idSecure().toString().matches(UuidPattern)),
            () -> assertThat(actualDbPoker.name()).isEqualTo(expectedDbPoker.name()),
            () -> assertThat(actualDbPoker.createdBy()).isEqualTo(InsecureUserFakeBuilder.defaultIdSecure1),

            () -> assertThat(actualDbTickets0.id()).isEqualTo(expectedDbTicket0.id()),
            () -> assertTrue(actualDbTickets0.idSecure().toString().matches(UuidPattern)),
            () -> assertThat(actualDbTickets0.pokerId()).isEqualTo(expectedDbTicket0.pokerId()),
            () -> assertThat(actualDbTickets0.name()).isEqualTo(expectedDbTicket0.name()),
            () -> assertThat(actualDbTickets0.isActive()).isEqualTo(expectedDbTicket0.isActive()),

            () -> assertThat(actualDbTickets1.id()).isEqualTo(expectedDbTicket1.id()),
            () -> assertTrue(actualDbTickets1.idSecure().toString().matches(UuidPattern)),
            () -> assertThat(actualDbTickets1.pokerId()).isEqualTo(expectedDbTicket1.pokerId()),
            () -> assertThat(actualDbTickets1.name()).isEqualTo(expectedDbTicket1.name()),
            () -> assertThat(actualDbTickets1.isActive()).isEqualTo(expectedDbTicket1.isActive())
        );
    }
}
