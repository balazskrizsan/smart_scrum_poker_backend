package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert3InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InsecureUserService_FindByIdSecureList extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert3InsecureUser.class})
    @SneakyThrows
    public void ThreeUsersDbPresetReadOneByIdSecureList_returnsOneInList()
    {
        // Arrange
        List<UUID> testedIdScecureList = new ArrayList<>()
        {{
            add(InsecureUserFakeBuilder.defaultIdSecure2);
        }};
        List<InsecureUser> expectedInsecureUsers = new InsecureUserFakeBuilder().build2AsList();


        // Act
        List<InsecureUser> actual = createInstance(InsecureUserService.class).findByIdSecureList(testedIdScecureList);

        // Assert
        assertThat(actual).isEqualTo(expectedInsecureUsers);
    }
}
