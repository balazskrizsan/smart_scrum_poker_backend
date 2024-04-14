package com.kbalazsworks.smartscrumpokerbackend.socket_api.interceptors;

import com.kbalazsworks.smartscrumpokerbackend.common.factories.LocalDateTimeFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.MultiValueMap;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class SocketChannelInterceptor implements ChannelInterceptor
{
    private final InsecureUserSessionsService insecureUserSessionsService;
    private final LocalDateTimeFactory localDateTimeFactory;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel)
    {
        MessageHeaders headers = message.getHeaders();
        MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);

        String simpSessionId;
        String insecureUserIdSecure;
        try
        {
            simpSessionId = Objects.requireNonNull(headers.get("simpSessionId")).toString();
            insecureUserIdSecure = Objects.requireNonNull(multiValueMap).get("insecureUserIdSecure").getFirst();
        }
        catch (NullPointerException e)
        {
            return message;
        }

        insecureUserSessionsService.add(new InsecureUserSession(
            UUID.fromString(insecureUserIdSecure),
            UUID.fromString(simpSessionId),
            localDateTimeFactory.create()
        ));

        return message;
    }
}
