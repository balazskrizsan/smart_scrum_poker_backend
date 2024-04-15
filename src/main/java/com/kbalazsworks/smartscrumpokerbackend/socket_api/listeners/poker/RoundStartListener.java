package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.RoundStartResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.RoundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.POKER_ROUND_START;

@Log4j2
@Controller
@RequiredArgsConstructor
public class RoundStartListener
{
    private final RoundService roundService;
    private final NotificationService notificationService;

    @MessageMapping("/poker.round.start/{pokerIdSecure}/{ticketId}")
    public void roundStartHandler(
        @Payload String message,
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException, PokerException
    {
        log.info("SocketListener:/poker.round.start/{}/{}", pokerIdSecure, ticketId);

        roundService.start(pokerIdSecure, ticketId);

        notificationService.notifyPokerRoom(pokerIdSecure, new RoundStartResponse(ticketId), POKER_ROUND_START);
    }
}
