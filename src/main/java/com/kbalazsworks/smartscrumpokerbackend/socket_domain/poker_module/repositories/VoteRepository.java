package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.VoteRecord;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class VoteRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.Vote voteTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.Vote.VOTE;

    public void create(@NonNull Vote vote)
    {
        getDSLContext()
            .insertInto(
                voteTable,
                voteTable.TICKET_ID,
                voteTable.UNCERTAINTY,
                voteTable.COMPLEXITY,
                voteTable.EFFORT,
                voteTable.CALCULATED_POINT,
                voteTable.CREATED_AT,
                voteTable.CREATED_BY
            ).values(
                vote.ticketId(),
                vote.uncertainty(),
                vote.complexity(),
                vote.effort(),
                vote.calculatedPoint(),
                vote.createdAt(),
                vote.createdBy()
            )
            .onDuplicateKeyUpdate()
            .set(voteTable.UNCERTAINTY, vote.uncertainty())
            .set(voteTable.COMPLEXITY, vote.complexity())
            .set(voteTable.EFFORT, vote.effort())
            .set(voteTable.CALCULATED_POINT, vote.calculatedPoint())
            .execute();
    }

    public Map<Long, Map<UUID, Vote>> getVotesWithTicketGroupByTicketIds(@NonNull List<Long> ticketIds)
    {
        return getDSLContext()
            .selectFrom(voteTable)
            .where(voteTable.TICKET_ID.in(ticketIds))
            .collect(
                Collectors.groupingBy(
                    VoteRecord::getTicketId,
                    Collectors.mapping(
                        r -> r.into(Vote.class),
                        Collectors.toMap(Vote::createdBy, Function.identity())
                    )
                )
            );
    }
}
