package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService
{
    private final SimpMessagingTemplate simpMessagingTemplate;

    public <T> void notifyPokerGame(
        @NonNull UUID pokerIdSecure,
        @NonNull T data,
        @NonNull SocketDestination socketDestination
    )
        throws ApiException
    {
        simpMessagingTemplate.convertAndSend(
            STR."/queue/reply-\{pokerIdSecure}",
            new ResponseEntityBuilder<T>().socketDestination(socketDestination).data(data).build()
        );
    }
}
