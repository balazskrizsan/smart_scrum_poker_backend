package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.TicketClosed;
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
public class TicketCloseListener
{
    private final SimpMessagingTemplate template;

    @MessageMapping("/poker.ticket.close/{pokerIdSecure}/{ticketId}")
    public void roundStartHandler(
        @Payload String message,
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException
    {
        log.info("SocketListener:/poker.ticket.close/{}/{}", pokerIdSecure, ticketId);

        template.convertAndSend(
            "/queue/reply-" + pokerIdSecure,
            new ResponseEntityBuilder<TicketClosed>()
                .socketDestination(SocketDestination.POKER_TICKET_CLOSED)
                .data(new TicketClosed(ticketId))
                .build()
        );
    }
}
