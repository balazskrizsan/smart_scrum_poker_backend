package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class PokerRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker pokersTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker.POKER;

    public void create(@NonNull Poker poker)
    {
        getDSLContext()
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
            .execute();
    }
}
