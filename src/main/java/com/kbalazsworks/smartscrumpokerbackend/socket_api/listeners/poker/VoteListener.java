package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.VoteRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.VoteResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.RequestMapperService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
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

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.SEND_POKER_VOTE;

@Log4j2
@Controller
@RequiredArgsConstructor
public class VoteListener
{
    private final SimpMessagingTemplate template;
    private final VoteService voteService;
    private final NotificationService notificationService;

    @MessageMapping("/poker.vote/{pokerIdSecure}/{ticketId}")
    public void roundStopHandler(
        @Payload VoteRequest voteRequest,
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException, StoryPointException, AccountException
    {
        log.info("SocketListener:/poker.vote/{}/{}: {}", pokerIdSecure, ticketId, voteRequest);

        InsecureUser insecureUser = voteService.vote(RequestMapperService.mapToEntity(voteRequest));

        notificationService.notifyPokerRoom(pokerIdSecure, new VoteResponse(insecureUser), SEND_POKER_VOTE);
    }
}
