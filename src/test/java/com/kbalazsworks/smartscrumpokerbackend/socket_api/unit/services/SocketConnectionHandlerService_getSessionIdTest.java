package com.kbalazsworks.smartscrumpokerbackend.socket_api.unit.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.SocketApiServiceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SocketConnectionHandlerService_getSessionIdTest extends AbstractTest
{
    @Autowired
    private SocketApiServiceFactory socketApiServiceFactory;

    @Test
    public void unavailableSessionId_throwsSessionException()
    {
        // Arrange
        MessageHeaders testedMessageHeaders = new MessageHeaders(Map.of(
            "simpSessionId",
            InsecureUserSessionFakeBuilder.defaultSessionId
        ));
        UUID expected = InsecureUserSessionFakeBuilder.defaultSessionId;

        // Act
        UUID actual = socketApiServiceFactory.getSocketConnectionHandlerService().getSessionId(testedMessageHeaders);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
