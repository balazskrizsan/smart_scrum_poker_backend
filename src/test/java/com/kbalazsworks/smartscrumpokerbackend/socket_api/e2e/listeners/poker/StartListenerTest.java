package com.kbalazsworks.smartscrumpokerbackend.socket_api.e2e.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractE2eTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.StartResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.kbalazsworks.smartscrumpokerbackend.helpers.CustomAsserts.softPokerAssert;
import static com.kbalazsworks.smartscrumpokerbackend.helpers.CustomAsserts.softTicketAssert;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class StartListenerTest extends AbstractE2eTest
{
    CompletableFuture<ResponseEntity_ResponseData_StartResponse> completableFuture = new CompletableFuture<>();

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

        var testedPokerStartRequest = new StartRequest(
            PokerFakeBuilder.defaultSprintName,
            List.of(TicketFakeBuilder.defaultName1, TicketFakeBuilder.defaultName2),
            InsecureUserFakeBuilder.defaultIdSecure1
        );

        Poker expectedDbPoker = new PokerFakeBuilder().id(1L).build();
        Ticket expectedDbTicket0 = new TicketFakeBuilder().id(1L).pokerId(expectedDbPoker.id()).build();
        Ticket expectedDbTicket1 = new TicketFakeBuilder().id2(2L).pokerId2(expectedDbPoker.id()).build2();
        String expectedHttpStatus = HttpStatus.OK.getReasonPhrase();

        // Act
        stompSession.subscribe("/user/queue/reply", new PokerStartStompFrameHandler());
        stompSession.send("/app/poker.start", testedPokerStartRequest);

        // Assert
        completableFuture.join();

        ResponseEntity_ResponseData_StartResponse actualResponse = completableFuture.get();

        Poker actualDbPoker = getDslContext().selectFrom(pokerTable).fetchOneInto(Poker.class);
        List<Ticket> actualDbTickets = getDslContext().selectFrom(ticketTable).fetchInto(Ticket.class);
        Ticket actualDbTickets0 = actualDbTickets.get(0);
        Ticket actualDbTickets1 = actualDbTickets.get(1);

        assertAll(
            () -> assertThat(actualResponse.statusCode).isEqualTo(expectedHttpStatus),
            () -> assertThat(actualResponse.body.data.poker()).isEqualTo(actualDbPoker),
            () -> softPokerAssert(actualDbPoker, expectedDbPoker),
            () -> softTicketAssert(actualDbTickets0, expectedDbTicket0),
            () -> softTicketAssert(actualDbTickets1, expectedDbTicket1)
        );
    }

    private class PokerStartStompFrameHandler implements StompFrameHandler
    {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders)
        {
            return ResponseEntity_ResponseData_StartResponse.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o)
        {
            completableFuture.complete((ResponseEntity_ResponseData_StartResponse) o);
        }
    }

    private record ResponseEntity_ResponseData_StartResponse(
        ResponseData_StartResponse body,
        Map<String, String> headers,
        String statusCode,
        int statusCodeValue
    )
    {
    }

    private record ResponseData_StartResponse(
        StartResponse data,
        Boolean success,
        int errorCode,
        String requestId,
        String socketResponseDestination
    )
    {
    }
}
