package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.account_service.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class InsecureUserSessionsService_AddTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert1InsecureUser.class}, transactional = true, truncate = true, truncateAfter = true)
    @SneakyThrows
    public void addTwoItemsWithOnDuplicateKeyIgnore_createsOneRecord()
    {
        // Arrange
        InsecureUserSession testedInsecureUserSession = new InsecureUserSessionFakeBuilder().build();
        List<InsecureUserSession> expectedInsecureUserSession = new InsecureUserSessionFakeBuilder().buildAsList();

        // Act
        InsecureUserSessionsService service = createInstance(InsecureUserSessionsService.class);
        boolean actualHasInsert1 = service.add(testedInsecureUserSession);
        boolean actualHasInsert2 = service.add(testedInsecureUserSession);

        // Assert
        List<InsecureUserSession> actual = getDslContext()
            .selectFrom(insecureUserSessionsTable)
            .fetchInto(InsecureUserSession.class);

        assertAll(
            () -> assertThat(actual).isEqualTo(expectedInsecureUserSession),
            () -> assertThat(actualHasInsert1).isEqualTo(true),
            () -> assertThat(actualHasInsert2).isEqualTo(false)
        );
    }
}
