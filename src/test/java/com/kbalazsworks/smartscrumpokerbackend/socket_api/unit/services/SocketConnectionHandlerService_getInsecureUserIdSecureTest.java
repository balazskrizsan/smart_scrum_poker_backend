package com.kbalazsworks.smartscrumpokerbackend.socket_api.unit.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.exceptions.SocketException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketConnectionHandlerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER;
import static org.springframework.messaging.support.NativeMessageHeaderAccessor.NATIVE_HEADERS;

public class SocketConnectionHandlerService_getInsecureUserIdSecureTest extends AbstractTest
{
    @Test
    @SneakyThrows
    public void getInsecureUserIdSecureFromNativeHeaders_returnsInsecureUuid()
    {
        // Arrange
        MessageHeaders testedHeaders = new MessageHeaders(
            Map.of(
                CONNECT_MESSAGE_HEADER,
                new GenericMessage<>(
                    "",
                    Map.of(
                        NATIVE_HEADERS,
                        Map.of(
                            "insecureUserIdSecure",
                            List.of(InsecureUserFakeBuilder.defaultIdSecure1.toString())
                        )
                    )
                )
            )
        );
        UUID expectedInsecureUserIdSecure = InsecureUserFakeBuilder.defaultIdSecure1;

        // Act
        UUID actual = createInstance(SocketConnectionHandlerService.class).getInsecureUserIdSecure(testedHeaders);

        // Assert
        assertThat(actual).isEqualTo(expectedInsecureUserIdSecure);
    }

    @Test
    @SneakyThrows
    public void unavailableSessionId_throwsSessionException()
    {
        // Arrange
        MessageHeaders testedHeaders = new MessageHeaders(Map.of());

        // Act - Assert
        assertThatThrownBy(() -> createInstance(SocketConnectionHandlerService.class)
            .getInsecureUserIdSecure(testedHeaders)
        )
            .isInstanceOf((SocketException.class))
            .hasMessage("insecureUserIdSecure not found");
    }
}
