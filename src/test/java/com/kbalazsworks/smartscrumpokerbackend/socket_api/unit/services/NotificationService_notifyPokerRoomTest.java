package com.kbalazsworks.smartscrumpokerbackend.socket_api.unit.services;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.common_module.mocker.SimpMessagingTemplateMocker;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.POKER_ROOM_STATE;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NotificationService_notifyPokerRoomTest extends AbstractTest
{
    @Test
    @SneakyThrows
    public void notifyPokerRoomWithSimpMessagingTemplate_callsTemplate()
    {
        // Arrange
        SimpMessagingTemplate mock = new SimpMessagingTemplateMocker().getMock();

        UUID testedPokerIdSecure = PokerFakeBuilder.defaultIdSecure1;
        String testedData = "test";
        SocketDestination testedPokerRoomState = POKER_ROOM_STATE;

        String expectedDestination = STR."/queue/reply-\{PokerFakeBuilder.defaultIdSecure1}";
        ResponseEntity<ResponseData<String>> expectedData = new ResponseEntityBuilder<String>()
            .socketDestination(POKER_ROOM_STATE)
            .data("test")
            .build();

        // Act
        setOneTimeMock(NotificationService.class, SimpMessagingTemplate.class, mock);
        createInstance(NotificationService.class).notifyPokerRoom(testedPokerIdSecure, testedData, testedPokerRoomState);

        // Assert
        verify(mock, times(1)).convertAndSend(expectedDestination, expectedData);
    }
}
