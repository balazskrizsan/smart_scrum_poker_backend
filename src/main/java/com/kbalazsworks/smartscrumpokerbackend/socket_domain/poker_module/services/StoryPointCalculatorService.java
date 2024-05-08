package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.StoryPointException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteValues;
import org.springframework.stereotype.Service;

@Service
public class StoryPointCalculatorService
{
    public Short calculate(VoteValues voteValues) throws StoryPointException
    {
        return switch (voteValues)
        {
            case VoteValues v when v.questionMark() || v.coffeeMug() -> null;

            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 1 && v.effort() == 1 -> 1;
            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 1 && v.effort() == 2 -> 2;
            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 1 && v.effort() == 3 -> 5;

            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 2 && v.effort() == 1 -> 2;
            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 2 && v.effort() == 2 -> 3;
            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 2 && v.effort() == 3 -> 5;

            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 3 && v.effort() == 1 -> 3;
            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 3 && v.effort() == 2 -> 5;
            case VoteValues v when v.uncertainty() == 1 && v.complexity() == 3 && v.effort() == 3 -> 8;

            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 1 && v.effort() == 1 -> 3;
            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 1 && v.effort() == 2 -> 5;
            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 1 && v.effort() == 3 -> 8;

            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 2 && v.effort() == 1 -> 5;
            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 2 && v.effort() == 2 -> 5;
            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 2 && v.effort() == 3 -> 8;

            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 3 && v.effort() == 1 -> 5;
            case VoteValues v when v.uncertainty() == 2 && v.complexity() == 3 && v.effort() == 2 -> 8;

            case VoteValues v when v.uncertainty() == 3 && v.complexity() == 2 && v.effort() == 1 -> 8;
            case VoteValues v when v.uncertainty() == 3 && v.complexity() == 2 && v.effort() == 2 -> 8;
            case VoteValues v when v.uncertainty() == 3 && v.complexity() == 2 && v.effort() == 3 -> 13;

            case VoteValues v when v.uncertainty() == 3 && v.complexity() == 3 && v.effort() == 1 -> 8;
            case VoteValues v when v.uncertainty() == 3 && v.complexity() == 3 && v.effort() == 2 -> 13;
            case VoteValues v when v.uncertainty() == 3 && v.complexity() == 3 && v.effort() == 3 -> 13;

            default -> throw new StoryPointException("Not implemented");
        };
    }
}
