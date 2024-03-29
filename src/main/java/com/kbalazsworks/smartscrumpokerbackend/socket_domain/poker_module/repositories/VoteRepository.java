package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

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
                voteTable.UNCERTAINTY,
                voteTable.COMPLEXITY,
                voteTable.EFFORT,
                voteTable.CALCULATED_POINT,
                voteTable.CREATED_AT,
                voteTable.CREATED_BY
            ).values(
                vote.uncertainty(),
                vote.complexity(),
                vote.effort(),
                vote.calculatedPoint(),
                vote.createdAt(),
                vote.createdBy()
            )
            .execute();
    }
}
