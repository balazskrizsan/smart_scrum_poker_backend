package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.StartRoundService;
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
public class StartRoundListener
{
    private final SimpMessagingTemplate template;
    private final StartRoundService startRoundService;

    @MessageMapping("/poker.room.round.start/{pokerIdSecure}/{ticketId}")
    public void sendToRoom(
        @Payload String message,
        @DestinationVariable UUID pokerIdSecure,
        @DestinationVariable Long ticketId
    )
        throws ApiException
    {
        log.info("Listener:/poker.room.round.start/{}/{}", pokerIdSecure, ticketId);

        startRoundService.start(pokerIdSecure, ticketId);

//        template.convertAndSend(
//            "/queue/reply-" + roomId,
//            new ResponseEntityBuilder<String>().socketDestination(SocketDestination.POKER_START).data(message).build()
//        );
    }
}
