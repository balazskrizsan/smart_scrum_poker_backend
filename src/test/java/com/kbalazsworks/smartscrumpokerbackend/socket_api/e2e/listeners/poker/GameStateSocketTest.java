package com.kbalazsworks.smartscrumpokerbackend.socket_api.e2e.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2Poker;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3SessionsFor2Users;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert5TicketsAllInactive;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert5VotesFor2Poker3Ticket;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractE2eSocketTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.GameStateResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.VoteNewJoinerResponse;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.POKER_GAME_STATE;
import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.SEND_POKER_VOTE_NEW_JOINER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GameStateSocketTest extends AbstractE2eSocketTest
{
    CompletableFuture<ResponseEntity_ResponseData_VoteNewJoinerResponse> voteNewJoinerFuture = new CompletableFuture<>();
    CompletableFuture<ResponseEntity_ResponseData_GameStateResponse> gameStateFuture = new CompletableFuture<>();

    @Test
    @SqlPreset(presets = {
        Insert3InsecureUser.class,
        Insert3SessionsFor2Users.class,
        Insert2Poker.class,
        Insert5TicketsAllInactive.class,
        Insert5VotesFor2Poker3Ticket.class
    })
    @SneakyThrows
    public void stoppingVote_returnsVoteStatistic()
    {
        // Arrange
        StompSession stompSession = getStompSession();

        UUID testedPokerIdSecure = PokerFakeBuilder.defaultIdSecure1;
        UUID testedInstantInsecureUserId = InsecureUserFakeBuilder.defaultIdSecure1;

        String testedDestination = STR."/app/poker/game.state/\{testedPokerIdSecure}/\{testedInstantInsecureUserId}";
        String testedGameStateSubscribeUrl = "/user/queue/reply";
        String testedVoteNewJoinerSubscribeUrl = STR."/queue/reply-\{testedPokerIdSecure}";

        String expectedNewJoinerHttpStatus = HttpStatus.OK.getReasonPhrase();
        String expectedNewJoinerDestination = SEND_POKER_VOTE_NEW_JOINER.getValue();
        VoteNewJoinerResponse expectedNewJoinerData = new VoteNewJoinerResponse(new InsecureUserFakeBuilder().build());

        String expectedGameStateHttpStatus = HttpStatus.OK.getReasonPhrase();
        String expectedGameStateDestination = POKER_GAME_STATE.getValue();
        GameStateResponse expectedGameStaterData = new GameStateResponse(
            new PokerFakeBuilder().build(),
            new TicketFakeBuilder().build1to3AsList(),
            new InsecureUserFakeBuilder().buildAsList(),
            Map.of(
                TicketFakeBuilder.defaultId1, Map.of(
                    VoteFakeBuilder.defaultCreatedBy, new VoteFakeBuilder().build(),
                    VoteFakeBuilder.defaultCreatedBy2, new VoteFakeBuilder().build2()
                ),
                TicketFakeBuilder.defaultId2, Map.of(
                    VoteFakeBuilder.defaultCreatedBy3, new VoteFakeBuilder().build3(),
                    VoteFakeBuilder.defaultCreatedBy4, new VoteFakeBuilder().build4()
                ),
                TicketFakeBuilder.defaultId3, Map.of(
                    VoteFakeBuilder.defaultCreatedBy5, new VoteFakeBuilder().build5()
                )
            ),
            new InsecureUserFakeBuilder().build(),
            new InsecureUserFakeBuilder().build(),
            new InsecureUserFakeBuilder().buildAsList()
        );

        // Act
        StompFrameHandler voteNewJoinerStompHandler = buildStompFrameHandler(
            voteNewJoinerFuture,
            ResponseEntity_ResponseData_VoteNewJoinerResponse.class
        );
        StompFrameHandler gameStateStompHandler = buildStompFrameHandler(
            gameStateFuture,
            ResponseEntity_ResponseData_GameStateResponse.class
        );
        stompSession.subscribe(testedGameStateSubscribeUrl, gameStateStompHandler);
        stompSession.subscribe(testedVoteNewJoinerSubscribeUrl, voteNewJoinerStompHandler);
        stompSession.send(testedDestination, "");

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(voteNewJoinerFuture, gameStateFuture);
        combinedFuture.get(3, TimeUnit.SECONDS);

        var actualVoteNewJoiner = voteNewJoinerFuture.get();
        var actualGameState = gameStateFuture.get();

        // Assert
        assertAll(
            () -> assertThat(actualVoteNewJoiner.body().data()).isEqualTo(expectedNewJoinerData),
            () -> assertThat(actualVoteNewJoiner.statusCode()).isEqualTo(expectedNewJoinerHttpStatus),
            () -> assertThat(actualVoteNewJoiner.body().socketResponseDestination()).isEqualTo(expectedNewJoinerDestination),

            () -> assertThat(actualGameState.body().data()).isEqualTo(expectedGameStaterData),
            () -> assertThat(actualGameState.statusCode()).isEqualTo(expectedGameStateHttpStatus),
            () -> assertThat(actualGameState.body().socketResponseDestination()).isEqualTo(expectedGameStateDestination)
        );
    }

    private record ResponseEntity_ResponseData_VoteNewJoinerResponse(
        ResponseData_VoteNewJoinerResponse body,
        Map<String, String> headers,
        String statusCode,
        int statusCodeValue
    )
    {
    }

    private record ResponseData_VoteNewJoinerResponse(
        VoteNewJoinerResponse data,
        Boolean success,
        int errorCode,
        String requestId,
        String socketResponseDestination
    )
    {
    }

    private record ResponseEntity_ResponseData_GameStateResponse(
        ResponseData_GameStateResponse body,
        Map<String, String> headers,
        String statusCode,
        int statusCodeValue
    )
    {
    }

    private record ResponseData_GameStateResponse(
        GameStateResponse data,
        Boolean success,
        int errorCode,
        String requestId,
        String socketResponseDestination
    )
    {
    }
}
