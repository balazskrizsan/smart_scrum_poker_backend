package com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.exceptions.SocketException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketConnectionHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.SessionException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.value_objects.DisconnectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class SocketDisconnectedService
{
    private final InsecureUserSessionsService insecureUserSessionsService;
    private final SocketConnectionHandlerService socketConnectionHandlerService;

    // @todo: test
    public DisconnectResponse disconnect(MessageHeaders headers) throws SessionException, SocketException
    {
        UUID sessionId = socketConnectionHandlerService.getSessionId(headers);
        InsecureUserSession insecureUserSession = insecureUserSessionsService.getInsecureUserSession(sessionId);

        insecureUserSessionsService.removeBySessionId(sessionId);

        return new DisconnectResponse(
            insecureUserSessionsService.countByIdSecure(insecureUserSession.insecureUserIdSecure()) == 0,
            insecureUserSession
        );
    }
}
