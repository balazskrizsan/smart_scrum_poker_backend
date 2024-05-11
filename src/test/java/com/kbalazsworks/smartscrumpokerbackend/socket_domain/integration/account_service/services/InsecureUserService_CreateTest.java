package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.account_service.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.common_module.mocker.UuidServiceMocker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class InsecureUserService_CreateTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset()
    @SneakyThrows
    public void validInsecureUserRequest_createsInsecureUserRecord()
    {
        // Arrange
        InsecureUser testedInsecureUser = new InsecureUserFakeBuilder().build();
        UUID testedSessionId = InsecureUserSessionFakeBuilder.defaultSessionId;

        InsecureUser expectedUser = new InsecureUserFakeBuilder().id(1L).build();
        InsecureUserSession expectedInsecureUserSession = new InsecureUserSessionFakeBuilder().build();

        UuidService uuidServiceMock = new UuidServiceMocker()
            .mockGetRandom(InsecureUserSessionFakeBuilder.defaultInsecureUserIdSecure)
            .getMock();

        // Act
        InsecureUser actualUserResponse = createInstance(InsecureUserService.class, uuidServiceMock)
            .create(testedInsecureUser, testedSessionId);

        // Assert
        DSLContext dslContex = getDslContext();
        InsecureUser actualUser = dslContex.selectFrom(insecureUserTable).fetchOneInto(InsecureUser.class);
        InsecureUserSession actualInsecureUserSession = dslContex.selectFrom(insecureUserSessionsTable)
            .fetchOneInto(InsecureUserSession.class);

        assertAll(
            () -> assertThat(actualUser).isEqualTo(expectedUser),
            () -> assertThat(actualUserResponse).isEqualTo(expectedUser),
            () -> assertThat(actualInsecureUserSession).isEqualTo(expectedInsecureUserSession)
        );
    }
}
