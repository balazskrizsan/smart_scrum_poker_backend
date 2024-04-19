package com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services;

import com.kbalazsworks.smartscrumpokerbackend.common.factories.LocalDateTimeFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketConnectionHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.value_objects.ConnectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class SocketConnectedService
{
    private final SocketConnectionHandlerService socketConnectionHandlerService;
    private final InsecureUserSessionsService insecureUserSessionsService;
    private final LocalDateTimeFactory localDateTimeFactory;

    // @todo: test
    public ConnectResponse connect(MessageHeaders headers)
    {
        UUID insecureUserIdSecure = socketConnectionHandlerService.getInsecureUserIdSecure(headers);
        UUID simpSessionId = socketConnectionHandlerService.getSessionId(headers);

        InsecureUserSession insecureUserSession = new InsecureUserSession(
            insecureUserIdSecure,
            simpSessionId,
            localDateTimeFactory.create()
        );
        log.info("Try save session: {}", insecureUserSession);

        insecureUserSessionsService.add(insecureUserSession);
        return new ConnectResponse(
            insecureUserSessionsService.countByIdSecure(insecureUserIdSecure) == 1,
            insecureUserSession
        );
    }
}