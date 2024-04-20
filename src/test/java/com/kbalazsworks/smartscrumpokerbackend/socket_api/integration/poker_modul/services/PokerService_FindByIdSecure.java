package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.poker_modul.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.PokerService;
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

public class PokerService_FindByIdSecure extends AbstractIntegrationTest
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
                    "classpath:test/sqls/_preset_insert_2_pokers.sql",
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
    public void SelectFromFilledDb_ReturnsOnlyOneEntity()
    {
        // Arrange
        UUID testedPokerInsecureId = PokerFakeBuilder.defaultIdSecure1;
        Poker expected = new PokerFakeBuilder().build();

        // Act
        Poker actual = createInstance(PokerService.class).findByIdSecure(testedPokerInsecureId);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
