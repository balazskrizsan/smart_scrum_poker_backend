package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteStop;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class VoteStartStopService
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

        Supplier<Stream<Vote>> valueStreamSupplier = () -> votes.values().stream();
        Supplier<Stream<Short>> calculatedPointStreamSupplier = () -> valueStreamSupplier.get().map(Vote::calculatedPoint);

        double avg = valueStreamSupplier.get().mapToDouble(Vote::calculatedPoint).average().orElseThrow();
        short min = calculatedPointStreamSupplier.get().min(Short::compare).orElseThrow();
        short max = calculatedPointStreamSupplier.get().max(Short::compare).orElseThrow();

        return new VoteStop(votes, avg, min, max);
    }
}
