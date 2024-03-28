package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.VoteRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.RequestMapperService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.StoryPointException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.VoteService;
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
public class VoteListener
{
    private final SimpMessagingTemplate template;
    private final VoteService voteService;

    @MessageMapping("/poker.vote/{pokerIdSecure}/{ticketId}")
    public void roundStopHandler(
        @Payload VoteRequest voteRequest,
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException, StoryPointException
    {
        log.info("SocketListener:/poker.vote/{}/{}: {}", pokerIdSecure, ticketId, voteRequest);

        voteService.vote(RequestMapperService.mapToEntity(voteRequest));

//        template.convertAndSend(
//            "/queue/reply-" + pokerIdSecure,
//            new ResponseEntityBuilder<StartRoundResponse>()
//                .socketDestination(SocketDestination.POKER_ROUND_STOP)
//                .data(new StartRoundResponse(ticketId))
//                .build()
//        );
    }
}
