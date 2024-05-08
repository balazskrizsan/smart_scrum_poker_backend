package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.poker;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.StartResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.RequestMapperService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.StartService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPoker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPokerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
public class StartListener
{
    private final StartService startService;

    @MessageMapping("/poker/start")
    @SendToUser("/queue/reply")
    public ResponseEntity<ResponseData<StartResponse>> startListener(@Payload StartRequest request)
        throws PokerException, ApiException, AccountException
    {
        log.info("StartListener:/poker/start: {}", request);

        StartPoker startPoker = RequestMapperService.mapToEntity(request);

        StartPokerResponse startPokerResponse = startService.start(startPoker.poker(), startPoker.tickets());

        return new ResponseEntityBuilder<StartResponse>()
            .socketDestination(SocketDestination.POKER_START)
            .data(new StartResponse(startPokerResponse.poker()))
            .build();
    }
}
