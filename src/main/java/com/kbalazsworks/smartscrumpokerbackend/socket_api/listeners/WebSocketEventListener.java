package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners;


import com.kbalazsworks.smartscrumpokerbackend.common.factories.LocalDateTimeFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER;
import static org.springframework.messaging.support.NativeMessageHeaderAccessor.NATIVE_HEADERS;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener
{
    private final InsecureUserSessionsService insecureUserSessionsService;
    private final LocalDateTimeFactory localDateTimeFactory;

    @EventListener
    public void handleWebSocketConnectListener(@NonNull SessionConnectedEvent event)
    {
        log.info("Socket connection opened: {}", event);

        try
        {
            MessageHeaders headers = event.getMessage().getHeaders();
            Map<String, List<String>> nativeHeaders = getNativeHeaders(headers);

            String simpSessionId = Objects.requireNonNull(headers.get("simpSessionId")).toString();
            String insecureUserIdSecure = Objects.requireNonNull(nativeHeaders).get("insecureUserIdSecure").getFirst();

            InsecureUserSession insecureUserSession = new InsecureUserSession(
                UUID.fromString(insecureUserIdSecure),
                UUID.fromString(simpSessionId),
                localDateTimeFactory.create()
            );
            log.info("Try save session: {}", insecureUserSession);

            insecureUserSessionsService.add(insecureUserSession);
        }
        catch (Exception e)
        {
            log.error("");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(@NonNull SessionDisconnectEvent event)
    {
        log.info("Socket connection closed: {}", event);
        log.info(insecureUserSessionsService.toString());
    }

    private Map<String, List<String>> getNativeHeaders(@NonNull MessageHeaders headers)
    {
        GenericMessage<?> genericMessage = headers.get(CONNECT_MESSAGE_HEADER, GenericMessage.class);

        return genericMessage.getHeaders().get(NATIVE_HEADERS, Map.class);
    }
}
