package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.services;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1InGamePlayerForPoker2;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2Poker;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3TicketsAllInactive;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.common_module.mocker.SimpMessagingTemplateMocker;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.SessionResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketNotificationHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.SESSION_CREATED_OR_UPDATED;
import static org.mockito.Mockito.verify;

public class SocketNotificationHandlerService_notifyPokerGamesWithNewSessionTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {
        Insert2InsecureUser.class,
        Insert2Poker.class,
        Insert1InGamePlayerForPoker2.class,
        Insert3TicketsAllInactive.class
    })
    @SneakyThrows
    public void newSessionNotifyPokerGamesFromFilledDb_callsNotificationService()
    {
        // Arrange
        UUID testedInsecureUserIdSecure = InsecureUserFakeBuilder.defaultIdSecure1;

        SimpMessagingTemplate mock = new SimpMessagingTemplateMocker().getMock();

        String expectedDestination = STR."/queue/reply-\{PokerFakeBuilder.defaultIdSecure2}";
        ResponseEntity<ResponseData<SessionResponse>> expectedData = new ResponseEntityBuilder<SessionResponse>()
            .socketDestination(SESSION_CREATED_OR_UPDATED)
            .data(new SessionResponse(new InsecureUserFakeBuilder().build()))
            .build();

        // Act
        setOneTimeMock(NotificationService.class, mock);
        createInstance(SocketNotificationHandlerService.class)
            .notifyPokerGameWithNewSession(testedInsecureUserIdSecure);

        // Assert
        verify(mock).convertAndSend(expectedDestination, expectedData);
    }
}
