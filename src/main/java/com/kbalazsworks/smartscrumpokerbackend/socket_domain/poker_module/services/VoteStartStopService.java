package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VotesWithVoteStat;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoteStartStopService
{
    TicketService ticketService;
    VoteService voteService;
    PokerService pokerService;

    public void start(@NonNull UUID pokerIdSecure, long ticketId) throws PokerException
    {
        pokerService.findByIdSecure(pokerIdSecure);
        ticketService.activate(ticketId);
    }

    public VotesWithVoteStat stop(@NonNull UUID pokerIdSecure, long ticketId) throws PokerException
    {
        pokerService.findByIdSecure(pokerIdSecure);
        ticketService.deactivate(ticketId);

        return voteService.getStatByTicketId(ticketId);
    }
}
