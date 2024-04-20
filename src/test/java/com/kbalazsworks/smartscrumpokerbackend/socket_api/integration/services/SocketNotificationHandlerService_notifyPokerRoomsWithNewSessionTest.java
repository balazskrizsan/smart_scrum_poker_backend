package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.services;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.common_module.mocker.SimpMessagingTemplateMocker;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.SessionResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketNotificationHandlerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.SESSION_CREATED_OR_UPDATED;
import static org.mockito.Mockito.verify;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class SocketNotificationHandlerService_notifyPokerRoomsWithNewSessionTest extends AbstractIntegrationTest
{
    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_2_insecure_user.sql",
                    "classpath:test/sqls/_preset_insert_3_pokers.sql",
                    "classpath:test/sqls/_preset_insert_1_in_game_players_user.sql",
                    "classpath:test/sqls/_preset_insert_3_tickets_all_inactive.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @SneakyThrows
    public void newSessionNotifyPokerRoomsFromFilledDb_callsNotificationService()
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
        setOneTimeMock(NotificationService.class, SimpMessagingTemplate.class, mock);
        createInstance(SocketNotificationHandlerService.class)
            .notifyPokerRoomsWithNewSession(testedInsecureUserIdSecure);

        // Assert
        verify(mock).convertAndSend(expectedDestination, expectedData);
    }
}
