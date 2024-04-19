package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import lombok.NonNull;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER;
import static org.springframework.messaging.support.NativeMessageHeaderAccessor.NATIVE_HEADERS;

@Service
public class SocketConnectionHandlerService
{
    public UUID getSessionId(@NonNull MessageHeaders headers)
    {
        return UUID.fromString(Objects.requireNonNull(headers.get("simpSessionId")).toString());
    }

    // @todo: test
    public UUID getInsecureUserIdSecure(@NonNull MessageHeaders headers)
    {
        String insecureUserIdSecure = Objects.requireNonNull(getNativeHeaders(headers))
            .get("insecureUserIdSecure")
            .getFirst();

        return UUID.fromString(insecureUserIdSecure);
    }

    // @todo: test
    public Map<String, List<String>> getNativeHeaders(@NonNull MessageHeaders headers)
    {
        GenericMessage<?> genericMessage = headers.get(CONNECT_MESSAGE_HEADER, GenericMessage.class);

        return genericMessage.getHeaders().get(NATIVE_HEADERS, Map.class);
    }
}
