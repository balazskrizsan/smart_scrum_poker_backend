package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class InsecureUserService_searchUsersWithActiveSession extends AbstractIntegrationTest
{
    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_3_insecure_user.sql",
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
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_3_insecure_user.sql",
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
