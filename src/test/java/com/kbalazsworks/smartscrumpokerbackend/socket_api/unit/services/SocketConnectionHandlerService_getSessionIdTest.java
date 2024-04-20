package com.kbalazsworks.smartscrumpokerbackend.socket_api.unit.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.exceptions.SocketException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketConnectionHandlerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SocketConnectionHandlerService_getSessionIdTest extends AbstractTest
{
    @Test
    @SneakyThrows
    public void availableSessionId_returnsTheSessionUuid()
    {
        // Arrange
        MessageHeaders testedMessageHeaders = new MessageHeaders(Map.of(
            "simpSessionId",
            InsecureUserSessionFakeBuilder.defaultSessionId
        ));
        UUID expected = InsecureUserSessionFakeBuilder.defaultSessionId;

        // Act
        UUID actual = createInstance(SocketConnectionHandlerService.class).getSessionId(testedMessageHeaders);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @SneakyThrows
    public void unavailableSessionId_throwsSessionException()
    {
        // Arrange
        MessageHeaders testedMessageHeaders = new MessageHeaders(Map.of());

        // Act - Assert
        assertThatThrownBy(() -> createInstance(SocketConnectionHandlerService.class).getSessionId(testedMessageHeaders))
            .isInstanceOf((SocketException.class))
            .hasMessage("simpSessionId not found");
    }
}
