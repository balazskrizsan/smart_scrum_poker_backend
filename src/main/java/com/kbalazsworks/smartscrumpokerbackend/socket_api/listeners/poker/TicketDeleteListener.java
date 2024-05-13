package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.TicketDeleteResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.TicketService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.POKER_TICKET_DELETE;

@Log4j2
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketDeleteListener
{
    NotificationService notificationService;
    TicketService ticketService;

    @MessageMapping("/poker/ticket.delete/{pokerIdSecure}/{ticketId}")
    public void ticketCloseListener(
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException
    {
        log.info("TicketCloseListener:/poker/ticket.delete/{}/{}", pokerIdSecure, ticketId);

        ticketService.delete(ticketId);

        notificationService.notifyPokerGame(pokerIdSecure, new TicketDeleteResponse(ticketId), POKER_TICKET_DELETE);
    }
}
