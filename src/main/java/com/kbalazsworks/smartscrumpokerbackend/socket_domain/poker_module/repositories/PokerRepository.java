package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import lombok.NonNull;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

@Repository
public class PokerRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker pokersTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker.POKER;

    public Long create(@NonNull Poker poker) throws PokerException
    {
        Record1<Long> newIdRecord = getDSLContext()
            .insertInto(
                pokersTable,
                pokersTable.ID_SECURE,
                pokersTable.NAME,
                pokersTable.CREATED_AT,
                pokersTable.CREATED_BY
            )
            .values(
                poker.idSecure(),
                poker.name(),
                poker.createdAt(),
                poker.createdBy()
            )
            .returningResult(pokersTable.ID)
            .fetchOne();

        if (null == newIdRecord)
        {
            throw new PokerException("Poker creation failed.");
        }

        return newIdRecord.getValue(pokersTable.ID);
    }
}
