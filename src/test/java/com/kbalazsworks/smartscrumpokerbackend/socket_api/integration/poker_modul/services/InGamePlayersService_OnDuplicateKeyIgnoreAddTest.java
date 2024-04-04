package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.poker_modul.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.InGamePlayerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.PokerModuleServiceFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.InGamePlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class InGamePlayersService_OnDuplicateKeyIgnoreAddTest extends AbstractIntegrationTest
{
    @Autowired
    private PokerModuleServiceFactory pokerModuleServiceFactory;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_1_poker.sql",
                    "classpath:test/sqls/_preset_insert_3_tickets_all_inactive.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void AddOnePlayerToTheGame_DbRecordCreates()
    {
        // Arrange
        InGamePlayer testedInGamePlayer = new InGamePlayerFakeBuilder().build();
        InGamePlayer expectedInGamePlayer = new InGamePlayerFakeBuilder().build();

        // Act
        this.pokerModuleServiceFactory.getInGamePlayersService().onDuplicateKeyIgnoreAdd(testedInGamePlayer);

        // Assert
        InGamePlayer actualGamePlayers = getDslContext().selectFrom(inGamePlayersTable).fetchOne().into(InGamePlayer.class);

        assertThat(actualGamePlayers).isEqualTo(expectedInGamePlayer);
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/_preset_insert_1_poker.sql",
                    "classpath:test/sqls/_preset_insert_3_tickets_all_inactive.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void AddSamePlayerTwiceToTheGame_IgnoreTheSecond()
    {
        // Arrange
        InGamePlayer testedInGamePlayer1 = new InGamePlayerFakeBuilder().build();
        InGamePlayer testedInGamePlayer2 = new InGamePlayerFakeBuilder()
            .createdAt(LocalDateTime.of(2040, 11, 22, 11, 22, 33))
            .build();
        InGamePlayer expectedInGamePlayer = new InGamePlayerFakeBuilder().build();

        // Act
        this.pokerModuleServiceFactory.getInGamePlayersService().onDuplicateKeyIgnoreAdd(testedInGamePlayer1);
        this.pokerModuleServiceFactory.getInGamePlayersService().onDuplicateKeyIgnoreAdd(testedInGamePlayer2);

        // Assert
        InGamePlayer actualGamePlayers = getDslContext().selectFrom(inGamePlayersTable).fetchOne().into(InGamePlayer.class);

        assertThat(actualGamePlayers).isEqualTo(expectedInGamePlayer);
    }
}