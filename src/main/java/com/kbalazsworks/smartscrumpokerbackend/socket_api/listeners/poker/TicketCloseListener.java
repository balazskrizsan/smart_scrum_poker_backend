package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.TicketClosed;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.POKER_TICKET_CLOSE;

@Log4j2
@Controller
@RequiredArgsConstructor
public class TicketCloseListener
{
    private final SimpMessagingTemplate template;
    private final NotificationService notificationService;

    @MessageMapping("/poker/ticket.close/{pokerIdSecure}/{ticketId}")
    public void ticketCloseListener(
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException
    {
        log.info("TicketCloseListener:/poker/ticket.close/{}/{}", pokerIdSecure, ticketId);

        notificationService.notifyPokerRoom(pokerIdSecure, new TicketClosed(ticketId), POKER_TICKET_CLOSE);
    }
}
