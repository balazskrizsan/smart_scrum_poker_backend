package com.kbalazsworks.smartscrumpokerbackend.socket_api.e2e.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1Poker;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3TicketsAllInactive;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert5VotesFor2Poker3Ticket;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractE2eSocketTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.VoteStopResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteStop;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EndListenerSocketTest extends AbstractE2eSocketTest
{
    CompletableFuture<ResponseEntity_ResponseData_RoundEndResponse> responseFuture = new CompletableFuture<>();

    @Test
    @SqlPreset(presets = {Insert1Poker.class, Insert3TicketsAllInactive.class, Insert5VotesFor2Poker3Ticket.class})
    @SneakyThrows
    public void stoppingVote_returnsVoteStatistic()
    {
        // Arrange
        StompSession stompSession = getStompSession();

        UUID testedPokerIdSecret = PokerFakeBuilder.defaultIdSecure1;
        long testedTicketId = TicketFakeBuilder.defaultId2;
        String testedDestination = STR."/app/poker.round.stop/\{testedPokerIdSecret}/\{testedTicketId}";
        String testedSubscribeUrl = STR."/queue/reply-\{testedPokerIdSecret}";

        String expectedHttpStatus = HttpStatus.OK.getReasonPhrase();
        VoteStopResponse expectedResponse = new VoteStopResponse(
            PokerFakeBuilder.defaultIdSecure1,
            TicketFakeBuilder.defaultId2,
            new VoteStop(
                Map.of(
                    VoteFakeBuilder.defaultCreatedBy3, new VoteFakeBuilder().build3(),
                    VoteFakeBuilder.defaultCreatedBy4, new VoteFakeBuilder().build4()
                ),
                9,
                VoteFakeBuilder.defaultCalculatedPoint,
                VoteFakeBuilder.defaultCalculatedPoint4
            )
        );

        // Act
        StompFrameHandler stompHandler = buildStompFrameHandler(
            responseFuture,
            ResponseEntity_ResponseData_RoundEndResponse.class
        );
        stompSession.subscribe(testedSubscribeUrl, stompHandler);
        stompSession.send(testedDestination, "{}");
        responseFuture.join();
        ResponseEntity_ResponseData_RoundEndResponse actual = responseFuture.get();

        // Assert
        assertAll(
            () -> assertThat(actual.statusCode).isEqualTo(expectedHttpStatus),
            () -> assertThat(actual.body().data()).isEqualTo(expectedResponse)
        );
    }

    private record ResponseEntity_ResponseData_RoundEndResponse(
        ResponseData_RoundEndResponse body,
        Map<String, String> headers,
        String statusCode,
        int statusCodeValue
    )
    {
    }

    private record ResponseData_RoundEndResponse(
        VoteStopResponse data,
        Boolean success,
        int errorCode,
        String requestId,
        String socketResponseDestination
    )
    {
    }
}
