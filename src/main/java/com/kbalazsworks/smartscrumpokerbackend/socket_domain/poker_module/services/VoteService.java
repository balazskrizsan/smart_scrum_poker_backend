package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.StoryPointException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.VoteRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteService
{
    private final VoteRepository voteRepository;
    private final StoryPointCalculatorService storyPointCalculatorService;

    // @todo: unit test
    public void vote(@NonNull Vote vote) throws StoryPointException
    {
        Vote calculatedVote = new Vote(
            vote.id(),
            vote.uncertainty(),
            vote.complexity(),
            vote.effort(),
            storyPointCalculatorService.calculate(
                new VoteValues(false, false, vote.uncertainty(), vote.complexity(), vote.effort())
            ),
            vote.createdAt(),
            vote.createdBy()
        );

        voteRepository.create(calculatedVote);
    }
}
