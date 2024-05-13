package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.VoteStopResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.VoteStartStopService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VotesWithVoteStat;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class VoteStopListener
{
    private final SimpMessagingTemplate template;
    private final VoteStartStopService voteStartStopService;

    @MessageMapping("/poker/vote.stop/{pokerIdSecure}/{ticketId}")
    public void voteStopListener(
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException, PokerException
    {
        log.info("VoteStopListener:/poker/vote.stop/{}/{}", pokerIdSecure, ticketId);

        VotesWithVoteStat votesWithVoteStat = voteStartStopService.stop(pokerIdSecure, ticketId);

        template.convertAndSend(
            STR."/queue/reply-\{pokerIdSecure}",
            new ResponseEntityBuilder<VoteStopResponse>()
                .socketDestination(SocketDestination.POKER_ROUND_STOP)
                .data(new VoteStopResponse(pokerIdSecure, ticketId, votesWithVoteStat))
                .build()
        );
    }
}
