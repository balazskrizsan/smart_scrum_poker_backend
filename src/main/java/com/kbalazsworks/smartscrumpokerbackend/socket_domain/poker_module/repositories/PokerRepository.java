package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.PokerRecord;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import lombok.NonNull;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PokerRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker pokersTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker.POKER;

    public Poker create(@NonNull Poker poker) throws PokerException
    {
        Record newPoker = getDSLContext()
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
            .returningResult(pokersTable.fields())
            .fetchOne();

        if (null == newPoker)
        {
            throw new PokerException("Poker creation failed.");
        }

        return newPoker.into(Poker.class);
    }

    // @todo: test not found
    public Poker findByIdSecure(UUID pokerIdSecure) throws PokerException
    {
        PokerRecord record = getDSLContext()
            .selectFrom(pokersTable)
            .where(pokersTable.ID_SECURE.eq(pokerIdSecure))
            .fetchOne();

        if (null == record)
        {
            throw new PokerException(STR."Poker not found: id#\{pokerIdSecure}");
        }

        return record.into(Poker.class);
    }
}
