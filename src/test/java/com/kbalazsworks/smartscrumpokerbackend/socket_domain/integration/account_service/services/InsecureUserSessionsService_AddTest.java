package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.account_service.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class InsecureUserSessionsService_AddTest extends AbstractIntegrationTest
{
    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_1_insecure_user.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
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
