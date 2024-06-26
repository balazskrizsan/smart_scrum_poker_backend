package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.main;

import com.kbalazsworks.smartscrumpokerbackend.config.ApplicationProperties;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.exceptions.SocketException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketNotificationHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.SessionException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.SocketDisconnectedService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.value_objects.DisconnectResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class SocketDisconnectListener
{
    private final ApplicationProperties applicationProperties;
    private final SocketNotificationHandlerService socketNotificationHandlerService;
    private final SocketDisconnectedService socketDisconnectedService;

    // @todo: test
    @EventListener
    public void socketDisconnectListener(@NonNull SessionDisconnectEvent event)
        throws AccountException, SessionException, SocketException
    {
        if (!applicationProperties.isEnabledSocketConnectAndDisconnectListeners())
        {
            log.warn("Disconnect listener disabled");

            return;
        }

        log.info("Socket connection closed: {}", event);

        DisconnectResponse response = socketDisconnectedService.disconnect(event.getMessage().getHeaders());

        if (response.shouldSendNotification())
        {
            socketNotificationHandlerService.notifyPokerGameWithLeavingSession(
                response.insecureUserSession().insecureUserIdSecure()
            );
        }
    }
}
