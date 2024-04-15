package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.POKER_START;

@Log4j2
@Controller
@RequiredArgsConstructor
public class RoomListener
{
    private final SimpMessagingTemplate template;
    private final NotificationService notificationService;

    @MessageMapping("/poker-room-{pokerIdSecure}")
    public void roomHandler(@Payload String message, @DestinationVariable UUID pokerIdSecure)
        throws ApiException
    {
        log.info("Listener:/poker-room-pokerIdSecure/{}: {}", message, pokerIdSecure);

        notificationService.notifyPokerRoom(pokerIdSecure, message, POKER_START);
    }
}
