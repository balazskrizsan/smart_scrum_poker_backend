package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService
{
    private final SimpMessagingTemplate template;

    public <T> void notifyPokerRoom(UUID pokerIdSecure, T data, SocketDestination socketDestination) throws ApiException
    {
        template.convertAndSend(
            STR."/queue/reply-\{pokerIdSecure}",
            new ResponseEntityBuilder<T>().socketDestination(socketDestination).data(data).build()
        );
    }
}
