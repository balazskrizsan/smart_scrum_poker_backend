package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.StoryPointException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.VoteRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteValues;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VoteService
{
    private final InsecureUserService insecureUserService;
    private final StoryPointCalculatorService storyPointCalculatorService;
    private final VoteRepository voteRepository;

    public InsecureUser vote(@NonNull Vote vote) throws StoryPointException, AccountException
    {
        InsecureUser insecureUser = insecureUserService.findByIdSecure(vote.createdBy());

        Vote calculatedVote = new Vote(
            vote.id(),
            vote.ticketId(),
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

        return insecureUser;
    }

    public Map<Long, Map<Long, Vote>> getVotesWithTicketGroupByTicketIds(@NonNull List<Long> ticketIds)
    {
        return this.voteRepository.getVotesWithTicketGroupByTicketIds(ticketIds);
    }
}
