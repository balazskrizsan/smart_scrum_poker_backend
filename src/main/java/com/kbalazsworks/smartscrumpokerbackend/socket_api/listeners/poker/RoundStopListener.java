package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.VoteStopResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.RoundService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteStop;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class RoundStopListener
{
    private final SimpMessagingTemplate template;
    private final RoundService roundService;

    @MessageMapping("/poker.round.stop/{pokerIdSecure}/{ticketId}")
    public void roundStopHandler(
        @Payload String message,
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException, PokerException
    {
        log.info("SocketListener:/poker.round.stop/{}/{}", pokerIdSecure, ticketId);

        VoteStop voteStop = roundService.stop(pokerIdSecure, ticketId);

        template.convertAndSend(
            STR."/queue/reply-\{pokerIdSecure}",
            new ResponseEntityBuilder<VoteStopResponse>()
                .socketDestination(SocketDestination.POKER_ROUND_STOP)
                .data(new VoteStopResponse(pokerIdSecure, ticketId, voteStop.votes()))
                .build()
        );
    }
}
