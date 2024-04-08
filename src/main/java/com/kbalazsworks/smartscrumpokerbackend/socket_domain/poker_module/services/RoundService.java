package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteStop;
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

    public void start(UUID pokerIdSecure, long ticketId)
    {
        // @todo: exception if pokerIdSecure not exists
        ticketService.activate(ticketId);
    }

    // @todo: test
    public VoteStop stop(UUID pokerIdSecure, Long ticketId)
    {
        // @todo: exception if pokerIdSecure not exists
        ticketService.deactivate(ticketId);
        Map<UUID, Vote> votes = voteService.searchVotesWithTicketGroupByTicketId(ticketId);

        return new VoteStop(votes);
    }
}
