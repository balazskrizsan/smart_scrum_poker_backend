package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1InGamePlayer;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.InGamePlayerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.InGamePlayer;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.InGamePlayersService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InGamePlayersService_SearchUserSecureIdsByPokerIdSecureTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert1InGamePlayer.class})
    @SneakyThrows
    public void searchFromOneInsertedRecord_returnsOneElementInList()
    {
        // Arrange
        UUID testedPokerIdSecure = PokerFakeBuilder.defaultIdSecure1;
        List<InGamePlayer> expectedInGamePlayer = new InGamePlayerFakeBuilder().buildAsList();

        // Act
        List<InGamePlayer> actualInGamePlayers = createInstance(InGamePlayersService.class)
            .searchUserSecureIdsByPokerIdSecure(testedPokerIdSecure);

        // Assert
        assertThat(actualInGamePlayers).isEqualTo(expectedInGamePlayer);
    }
}
