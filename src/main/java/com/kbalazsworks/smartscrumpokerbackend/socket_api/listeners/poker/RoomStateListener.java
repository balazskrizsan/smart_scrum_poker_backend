package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.RoomStateResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.RoomStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class RoomStateListener
{
    private final RoomStateService roomStateService;

    @MessageMapping("/poker.room.state/{pokerIdSecure}")
    @SendToUser("/queue/reply")
    public ResponseEntity<ResponseData<RoomStateResponse>> pokerRoomState(
        @Payload String message,
        @DestinationVariable String pokerIdSecure
    ) throws ApiException
    {
        log.info("Listener:/poker.room.state/{}: {}", pokerIdSecure, message);

        return new ResponseEntityBuilder<RoomStateResponse>()
            .socketDestination(SocketDestination.POKER_ROOM_STATE)
            .data(roomStateService.get(UUID.fromString(pokerIdSecure)))
            .build();
    }
}
