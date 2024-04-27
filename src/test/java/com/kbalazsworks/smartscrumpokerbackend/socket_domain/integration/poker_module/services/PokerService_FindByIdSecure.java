package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert2Poker;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.PokerService;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PokerService_FindByIdSecure extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert2InsecureUser.class, Insert2Poker.class})
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
