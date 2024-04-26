package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.account_service.services;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.InsecureUserRecord;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.helpers.CustomAsserts.UuidPattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InsecureUserService_CreateTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {}, transactional = true, truncate = true, truncateAfter = true)
    @SneakyThrows
    public void validInsecureUserRequest_createsInsecureUserRecord()
    {
        // Arrange
        InsecureUser insecureUser = new InsecureUserFakeBuilder().build();
        InsecureUser expectedUser = new InsecureUserFakeBuilder().id(1L).build();

        // Act
        InsecureUser actualUserResponse = createInstance(InsecureUserService.class).create(insecureUser);

        // Assert
        InsecureUserRecord actualUserRecord = getDslContext().selectFrom(insecureUserTable).fetchOne();
        InsecureUser actualUser = actualUserRecord.into(InsecureUser.class);
        UUID actualUserIdSecure = actualUser.idSecure();
        actualUserRecord.setIdSecure(InsecureUserFakeBuilder.defaultIdSecure1);
        InsecureUser actualUserMocked = actualUserRecord.into(InsecureUser.class);

        assertAll(
            () -> assertThat(actualUserResponse).isEqualTo(actualUser),
            () -> assertTrue(actualUserIdSecure.toString().matches(UuidPattern)),
            () -> assertThat(actualUserMocked).isEqualTo(expectedUser)
        );
    }
}
