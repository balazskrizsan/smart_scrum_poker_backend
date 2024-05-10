package com.kbalazsworks.smartscrumpokerbackend.socket_api.e2e.listeners.account;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractE2eSocketTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.account.InsecureUserCreateRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.kbalazsworks.smartscrumpokerbackend.helpers.CustomAsserts.softInsecureUserAssert;
import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.SEND_ACCOUNT_INSECURE_USER_CREATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class InsecureUserCreateListenerSocketTest extends AbstractE2eSocketTest
{
    CompletableFuture<ResponseEntity_ResponseData_InsecureUser> newUserFuture = new CompletableFuture<>();

    @Test
    @SqlPreset
    public void sendValidUser_createsDbRecordAndReturnUser() throws Exception
    {
        // Arrange
        StompSession stompSession = getStompSession();

        InsecureUserCreateRequest testedInsecureUserCreateRequest =
            new InsecureUserCreateRequest(InsecureUserFakeBuilder.defaultUserName);
        String testedAccountCreatePath = "/app/account/insecure.user.create";
        String testedAccountCreateSubscribePath = "/user/queue/reply";

        String expectedHttpStatus = HttpStatus.OK.getReasonPhrase();
        String expectedDestination = SEND_ACCOUNT_INSECURE_USER_CREATE.getValue();
        InsecureUser expectedInsecureUser = new InsecureUserFakeBuilder().id(1L).build();

        // Act
        StompFrameHandler stompHandler = buildStompFrameHandler(
            newUserFuture,
            ResponseEntity_ResponseData_InsecureUser.class
        );
        stompSession.subscribe(testedAccountCreateSubscribePath, stompHandler);
        stompSession.send(testedAccountCreatePath, testedInsecureUserCreateRequest);

        ResponseEntity_ResponseData_InsecureUser actual = newUserFuture.get(3, TimeUnit.SECONDS);

        // Assert
        assertAll(
            () -> assertThat(actual.statusCode).isEqualTo(expectedHttpStatus),
            () -> assertThat(actual.body().socketResponseDestination).isEqualTo(expectedDestination),
            () -> softInsecureUserAssert(actual.body().data(), expectedInsecureUser)
        );
    }

    private record ResponseEntity_ResponseData_InsecureUser(
        ResponseData_InsecureUserResponse body,
        Map<String, String> headers,
        String statusCode,
        int statusCodeValue
    )
    {
    }

    private record ResponseData_InsecureUserResponse(
        InsecureUser data,
        Boolean success,
        int errorCode,
        String requestId,
        String socketResponseDestination
    )
    {
    }
}
