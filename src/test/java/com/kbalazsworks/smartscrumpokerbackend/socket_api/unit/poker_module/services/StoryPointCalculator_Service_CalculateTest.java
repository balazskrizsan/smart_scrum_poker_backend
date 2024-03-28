package com.kbalazsworks.smartscrumpokerbackend.socket_api.unit.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory.PokerModuleServiceFactory;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.StoryPointException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteValues;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StoryPointCalculator_Service_CalculateTest extends AbstractTest
{
    @Autowired
    private PokerModuleServiceFactory pokerModuleServiceFactory;

    @Test
    @SneakyThrows
    public void qweqweqweqwe()
    {
        // Arrange
        var testedVote = new VoteValues(false, false, (short) 10L, (short) 10L, (short) 10L);

        // Act - Assert
        assertThatThrownBy(() -> pokerModuleServiceFactory.getStoryPointCalculatorService().calculate(testedVote))
            .isInstanceOf((StoryPointException.class))
            .hasMessage("Not implemented");
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("validScenarios_returnsWithExpectedStoryPoint_provider")
    public void validScenarios_returnsWithExpectedStoryPoint(VoteValues voteValues, Short expectedCalculation)
    {
        // Arrange - In provider

        // Act
        var actualCalculation = pokerModuleServiceFactory.getStoryPointCalculatorService().calculate(voteValues);

        // Assert
        assertThat(actualCalculation).isEqualTo(expectedCalculation);
    }

    private static Stream<Arguments> validScenarios_returnsWithExpectedStoryPoint_provider()
    {
        return Stream.of(
            Arguments.of(
                new VoteValues(true, false, (short) 1, (short) 1, (short) 1),
                null
            ),
            Arguments.of(
                new VoteValues(false, true, (short) 1, (short) 1, (short) 1),
                null
            ),
            Arguments.of(
                new VoteValues(false, false, (short) 1, (short) 1, (short) 1),
                (short) 1
            ),
            Arguments.of(
                new VoteValues(false, false, (short) 1, (short) 1, (short) 2),
                (short) 2
            ),
            Arguments.of(
                new VoteValues(false, false, (short) 1, (short) 1, (short) 3),
                (short) 5
            )
        );
    }
}
