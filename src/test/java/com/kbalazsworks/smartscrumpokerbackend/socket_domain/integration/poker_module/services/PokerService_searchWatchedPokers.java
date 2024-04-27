package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1InGamePlayerForPoker2;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2Poker;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.PokerService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PokerService_searchWatchedPokers extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert2InsecureUser.class, Insert2Poker.class, Insert1InGamePlayerForPoker2.class})
    @SneakyThrows
    public void selectFilledDb_returnsSelectedPokerAsIdMap()
    {
        // Arrange
        UUID testedInsecureUserIdSecure = InsecureUserFakeBuilder.defaultIdSecure1;
        Map<UUID, Poker> expected = Map.of(PokerFakeBuilder.defaultIdSecure2, new PokerFakeBuilder().build2());

        // Act
        Map<UUID, Poker> actual = createInstance(PokerService.class).searchWatchedPokers(testedInsecureUserIdSecure);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
