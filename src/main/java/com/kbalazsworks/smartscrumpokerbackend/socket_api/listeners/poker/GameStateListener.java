package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.GameStateResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.VoteNewJoinerResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.RequestMapperService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.GameStateService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.GameStateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class GameStateListener
{
    private final SimpMessagingTemplate template;
    private final GameStateService gameStateService;

    @MessageMapping("/poker/game.state/{pokerIdSecure}/{insecureUserId}")
    @SendToUser(value = "/queue/reply")
    public ResponseEntity<ResponseData<GameStateResponse>> gameStateListener(
        @Payload String message,
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable UUID insecureUserId
    ) throws ApiException, PokerException, AccountException
    {
        log.info("Listener:/poker/game.state/{}/{}: {}", pokerIdSecure, insecureUserId, message);
        GameStateResponse gameStateResponse = gameStateService.get(
            new GameStateRequest(pokerIdSecure, insecureUserId, RequestMapperService.getNow())
        );

        template.convertAndSend(
            STR."/queue/reply-\{pokerIdSecure}",
            new ResponseEntityBuilder<VoteNewJoinerResponse>()
                .socketDestination(SocketDestination.SEND_POKER_VOTE_NEW_JOINER)
                .data(new VoteNewJoinerResponse(gameStateResponse.currentInsecureUser()))
                .build()
        );

        return new ResponseEntityBuilder<GameStateResponse>()
            .socketDestination(SocketDestination.POKER_GAME_STATE)
            .data(gameStateResponse)
            .build();
    }
}
