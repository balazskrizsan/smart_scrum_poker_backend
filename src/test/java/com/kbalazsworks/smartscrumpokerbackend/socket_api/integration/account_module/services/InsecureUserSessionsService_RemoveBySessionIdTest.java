package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.account_module.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.AccountServiceFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class InsecureUserSessionsService_RemoveBySessionIdTest extends AbstractIntegrationTest
{
    @Autowired
    private AccountServiceFactory accountServiceFactory;

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
    public void remove1SessionIdFrom3sessionsDb_remains2records()
    {
        // Arrange
        UUID testedSessionId = InsecureUserSessionFakeBuilder.defaultSessionId2;
        List<InsecureUserSession> expected = List.of(
            new InsecureUserSessionFakeBuilder().build(),
            new InsecureUserSessionFakeBuilder().build3()
        );

        // Act
        accountServiceFactory.getInsecureUserSessionsService().removeBySessionId(testedSessionId);

        // Assert
        List<InsecureUserSession> actual = getDslContext()
            .selectFrom(insecureUserSessionsTable)
            .fetchInto(InsecureUserSession.class);

        assertThat(actual).isEqualTo(expected);
    }
}
