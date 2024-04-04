package com.kbalazsworks.smartscrumpokerbackend.socket_api.integration.poker_modul.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.VoteFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.PokerModuleServiceFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class VoteService_GetVotesWithTicketGroupByTicketIdsTest extends AbstractIntegrationTest
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
                    "classpath:test/sqls/_preset_insert_3_insecure_user.sql",
                    "classpath:test/sqls/_preset_insert_1_poker.sql",
                    "classpath:test/sqls/_preset_insert_3_tickets_all_inactive.sql",
                    "classpath:test/sqls/_preset_insert_5_votes_to_2_poker_3_ticket.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void selectFromDb_returnsGroupedVotes()
    {
        // Arrange
        List<Long> testedTicketIds = List.of(TicketFakeBuilder.defaultId1, TicketFakeBuilder.defaultId2);
        Map<Long, List<Vote>> expected = Map.of(
            TicketFakeBuilder.defaultId1, List.of(new VoteFakeBuilder().build(), new VoteFakeBuilder().build2()),
            TicketFakeBuilder.defaultId2, List.of(new VoteFakeBuilder().build3(), new VoteFakeBuilder().build4())
        );

        // Act
        Map<Long, List<Vote>> actual = pokerModuleServiceFactory
            .getVoteService()
            .getVotesWithTicketGroupByTicketIds(testedTicketIds);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
