package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.row;

@Repository
public class TicketRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.Ticket ticketTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.Ticket.TICKET;

    public void createAll(@NonNull List<Ticket> tickets)
    {
        getDSLContext()
            .insertInto(ticketTable, ticketTable.POKER_ID, ticketTable.NAME)
            .valuesOfRows(tickets.stream().map(r -> row(r.pokerId(), r.name())).toList())
            .execute();
    }

    public List<Ticket> searchByPokerId(@NonNull Long pokerId)
    {
        return getDSLContext()
            .selectFrom(ticketTable)
            .where(ticketTable.POKER_ID.eq(pokerId))
            .fetch()
            .into(Ticket.class);
    }
}
