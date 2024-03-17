package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.RequestMapperService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPoker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.StartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
public class StartAction
{
    private final StartService startService;

    @MessageMapping("/poker.start")
    @SendTo("/topic/public")
    public StartRequest sendMessage(@Payload StartRequest request) throws PokerException
    {
        StartPoker startPoker = RequestMapperService.mapToEntity(request);

        startService.start(startPoker.poker(), startPoker.tickets());

        return request;
    }
}
