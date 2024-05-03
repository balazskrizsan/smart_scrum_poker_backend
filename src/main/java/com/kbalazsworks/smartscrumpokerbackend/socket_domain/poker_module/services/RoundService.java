package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteStop;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoundService
{
    private final TicketService ticketService;
    private final VoteService voteService;
    private final PokerService pokerService;

    public void start(@NonNull UUID pokerIdSecure, long ticketId) throws PokerException
    {
        pokerService.findByIdSecure(pokerIdSecure);
        ticketService.activate(ticketId);
    }

    public VoteStop stop(@NonNull UUID pokerIdSecure, long ticketId) throws PokerException
    {
        pokerService.findByIdSecure(pokerIdSecure);
        ticketService.deactivate(ticketId);
        Map<UUID, Vote> votes = voteService.searchVotesWithTicketGroupByTicketId(ticketId);
        double avg = votes.values().stream().mapToDouble(Vote::calculatedPoint).average().orElse(Double.NaN);

        return new VoteStop(votes, avg);
    }
}
