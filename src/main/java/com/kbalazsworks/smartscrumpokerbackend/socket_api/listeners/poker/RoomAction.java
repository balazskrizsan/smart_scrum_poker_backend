package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
public class RoomAction
{
    private final SimpMessagingTemplate template;

    @MessageMapping("/poker-room-{roomId}")
    public void sendToRoom(@Payload String message, @DestinationVariable String roomId)
        throws ApiException
    {
        log.info("Listener:/poker-room-roomId/{}: {}", message, roomId);

        template.convertAndSend(
            "/queue/reply-" + roomId,
            new ResponseEntityBuilder<String>().socketDestination(SocketDestination.POKER_START).data(message).build()
        );
    }
}
