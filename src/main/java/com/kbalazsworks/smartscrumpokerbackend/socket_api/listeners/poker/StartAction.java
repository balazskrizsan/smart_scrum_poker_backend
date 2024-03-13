package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
public class StartAction
{
    @MessageMapping("/poker.start")
    @SendTo("/topic/public")
    public StartRequest sendMessage(@Payload StartRequest request)
    {
        log.info(request);

        return request;
    }
}
