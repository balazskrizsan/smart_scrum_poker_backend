package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.account_service.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.SessionException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserSessionsService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class InsecureUserSessionsService_getInsecureUserSessionTest extends AbstractIntegrationTest
{
    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_2_insecure_user.sql",
                    "classpath:test/sqls/_preset_insert_3_sessions_for_2_users.sql",
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
