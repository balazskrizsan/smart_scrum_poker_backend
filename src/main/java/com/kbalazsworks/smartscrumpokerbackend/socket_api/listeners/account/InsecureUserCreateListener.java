package com.kbalazsworks.smartscrumpokerbackend.socket_api.listeners.account;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.exceptions.SocketException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.account.InsecureUserCreateRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.RequestMapperService;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketConnectionHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
public class InsecureUserCreateListener
{
    private final InsecureUserService insecureUserService;
    private final SocketConnectionHandlerService socketConnectionHandlerService;

    @MessageMapping("/account/insecure.user.create")
    @SendToUser("/queue/reply")
    public ResponseEntity<ResponseData<InsecureUser>> insecureUserCreateHandler(
        @Payload InsecureUserCreateRequest insecureUserCreateRequest,
        MessageHeaders headers
    )
        throws AccountException, ApiException, SocketException
    {
        log.info("Listener:/account/insecure.user.create: {}", insecureUserCreateRequest);

        InsecureUser newInsecureUser = insecureUserService.create(
            RequestMapperService.mapToEntity(insecureUserCreateRequest),
            socketConnectionHandlerService.getSessionId(headers)
        );

        return new ResponseEntityBuilder<InsecureUser>()
            .socketDestination(SocketDestination.SEND_ACCOUNT_INSECURE_USER_CREATE)
            .data(newInsecureUser)
            .build();
    }
}
