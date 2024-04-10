package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.account_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.InsecureUserRecord;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.AccountServiceFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.helpers.CustomAsserts.UuidPattern;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class InsecureUserService_CreateTest extends AbstractIntegrationTest
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
    public void validInsecureUserRequest_createsInsecureUserRecord()
    {
        // Arrange
        InsecureUser insecureUser = new InsecureUserFakeBuilder().build();
        InsecureUser expectedUser = new InsecureUserFakeBuilder().id(1L).build();

        // Act
        InsecureUser actualUserResponse = accountServiceFactory.getInsecureUserService().create(insecureUser);

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
