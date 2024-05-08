package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.RoundStartResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.VoteStartStopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.POKER_ROUND_START;

@Log4j2
@Controller
@RequiredArgsConstructor
public class VoteStartListener
{
    private final VoteStartStopService voteStartStopService;
    private final NotificationService notificationService;

    @MessageMapping("/poker/vote.start/{pokerIdSecure}/{ticketId}")
    public void voteStartListener(
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException, PokerException
    {
        log.info("VoteStartListener:/poker/vote.start/{}/{}", pokerIdSecure, ticketId);

        voteStartStopService.start(pokerIdSecure, ticketId);

        notificationService.notifyPokerGame(pokerIdSecure, new RoundStartResponse(ticketId), POKER_ROUND_START);
    }
}
