package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.StoryPointException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.VoteRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteStat;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VoteValues;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VotesWithVoteStat;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoteService
{
    InsecureUserService insecureUserService;
    StoryPointCalculatorService storyPointCalculatorService;
    VoteRepository voteRepository;

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

    // @todo: rename to search
    public Map<Long, Map<UUID, Vote>> getVotesWithTicketGroupByTicketIds(@NonNull List<Long> ticketIds)
    {
        return voteRepository.getVotesWithTicketGroupByTicketIds(ticketIds);
    }

    // @todo: test
    public Map<UUID, Vote> searchVotesWithTicketGroupByTicketId(long ticketId)
    {
        Map<Long, Map<UUID, Vote>> result = voteRepository.getVotesWithTicketGroupByTicketIds(List.of(ticketId));

        return null == result ? Map.of() : result.get(ticketId);
    }

    public VotesWithVoteStat getStatByTicketId(long ticketId)
    {
        Map<UUID, Vote> votes = searchVotesWithTicketGroupByTicketId(ticketId);

        return calculateStat(votes);
    }

    public Map<Long, VotesWithVoteStat> getStatByTicketIds(@NonNull List<Long> tickedIds)
    {
        Map<Long, Map<UUID, Vote>> votes = voteRepository.getVotesWithTicketGroupByTicketIds(tickedIds);

        Map<Long, VotesWithVoteStat> votesWithVoteStats = new HashMap<>();

        votes.forEach((key, voteMap) -> votesWithVoteStats.put(key, calculateStat(voteMap)));

        return votesWithVoteStats;
    }

    private VotesWithVoteStat calculateStat(@NonNull Map<UUID, Vote> votes)
    {
        Supplier<Stream<Vote>> valueStreamSupplier = () -> votes.values().stream();
        Supplier<Stream<Short>> calculatedPointStreamSupplier = () -> valueStreamSupplier.get().map(Vote::calculatedPoint);

        double avg = valueStreamSupplier.get().mapToDouble(Vote::calculatedPoint).average().orElseThrow();
        short min = calculatedPointStreamSupplier.get().min(Short::compare).orElseThrow();
        short max = calculatedPointStreamSupplier.get().max(Short::compare).orElseThrow();

        return new VotesWithVoteStat(votes, new VoteStat(avg, min, max));
    }
}
