package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.poker_modul.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.RoomStateRequestFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.RoomStateResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.RoomStateService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.RoomStateRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class RoomStateService_GetTest extends AbstractIntegrationTest
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
                    "classpath:test/sqls/_preset_insert_5_tickets_all_inactive.sql",
                    "classpath:test/sqls/_preset_insert_5_votes_to_2_poker_3_ticket.sql",
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
    public void RequestingARoomState_returnRoomStateAndSetTheInGamePlayer()
    {
        // Arrange
        RoomStateRequest testedRoomStateRequest = new RoomStateRequestFakeBuilder().build();

        RoomStateResponse expected = new RoomStateResponse(
            new PokerFakeBuilder().build(),
            new TicketFakeBuilder().build1to3AsList(),
            new InsecureUserFakeBuilder().buildAsList(),
            Map.of(
                TicketFakeBuilder.defaultId1, Map.of(
                    VoteFakeBuilder.defaultCreatedBy, new VoteFakeBuilder().build(),
                    VoteFakeBuilder.defaultCreatedBy2, new VoteFakeBuilder().build2()
                ),
                TicketFakeBuilder.defaultId2, Map.of(
                    VoteFakeBuilder.defaultCreatedBy3, new VoteFakeBuilder().build3(),
                    VoteFakeBuilder.defaultCreatedBy4, new VoteFakeBuilder().build4()
                ),
                TicketFakeBuilder.defaultId3, Map.of(
                    VoteFakeBuilder.defaultCreatedBy5, new VoteFakeBuilder().build5()
                )
            ),
            new InsecureUserFakeBuilder().build(),
            new InsecureUserFakeBuilder().build()
        );

        // Act
        RoomStateResponse actual = createInstance(RoomStateService.class).get(testedRoomStateRequest);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
