package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.account_service.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3SessionsFor2Users;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.SessionException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InsecureUserSessionsService_getInsecureUserSessionTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert2InsecureUser.class, Insert3SessionsFor2Users.class})
    @SneakyThrows
    public void selectFromFilledDb_returnSelectedInsecureUserSession() throws SessionException
    {
        // Arrange
        UUID testedSessionId = InsecureUserSessionFakeBuilder.defaultSessionId;
        InsecureUserSession expected = new InsecureUserSessionFakeBuilder().build();

        // Act
        InsecureUserSession actual = createInstance(InsecureUserSessionsService.class)
            .getInsecureUserSession(testedSessionId);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
