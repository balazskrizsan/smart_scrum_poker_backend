package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.main;

import com.kbalazsworks.smartscrumpokerbackend.config.ApplicationProperties;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.exceptions.SocketException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketNotificationHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.SocketConnectedService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.value_objects.ConnectResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class SocketConnectListener
{
    private final ApplicationProperties applicationProperties;
    private final SocketNotificationHandlerService socketNotificationHandlerService;
    private final SocketConnectedService socketConnectedService;

    // @todo: test
    @EventListener
    public void socketConnectListener(@NonNull SessionConnectedEvent event) throws AccountException, SocketException
    {
        if (!applicationProperties.isEnabledSocketConnectAndDisconnectListeners())
        {
            log.warn("Connect listener disabled");

            return;
        }

        log.info("Socket connection opened: {}", event);

        ConnectResponse response = socketConnectedService.connect(event.getMessage().getHeaders());

        if (response.shouldSendNotification())
        {
            socketNotificationHandlerService.notifyPokerGameWithNewSession(
                response.insecureUserSession().insecureUserIdSecure()
            );
        }
    }
}
