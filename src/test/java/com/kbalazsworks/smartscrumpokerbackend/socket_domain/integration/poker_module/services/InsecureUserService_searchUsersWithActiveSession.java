package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3SessionsFor2Users;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InsecureUserService_searchUsersWithActiveSession extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert2InsecureUser.class, Insert3SessionsFor2Users.class})
    @SneakyThrows
    public void selectFromInsecureUsersAndSessionTable_returnsUsersWithSession()
    {
        // Arrange
        List<UUID> testedInsecureUserIdSecures = List.of(
            InsecureUserFakeBuilder.defaultIdSecure2,
            InsecureUserFakeBuilder.defaultIdSecure2
        );
        List<InsecureUser> expected = new InsecureUserFakeBuilder().build2AsList();

        // Act
        List<InsecureUser> actual = createInstance(InsecureUserService.class)
            .searchUsersWithActiveSession(testedInsecureUserIdSecures);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @SqlPreset(presets = {Insert3InsecureUser.class})
    @SneakyThrows
    public void selectUsersWithoutSession_returnsEmptyList()
    {
        // Arrange
        List<UUID> testedInsecureUserIdSecures = List.of(
            InsecureUserFakeBuilder.defaultIdSecure1,
            InsecureUserFakeBuilder.defaultIdSecure2
        );

        // Act
        List<InsecureUser> actual = createInstance(InsecureUserService.class)
            .searchUsersWithActiveSession(testedInsecureUserIdSecures);

        // Assert
        assertThat(actual.isEmpty()).isTrue();
    }
}
