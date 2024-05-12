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
    public void createAll(@NonNull List<Ticket> tickets)
    {
        getDSLContext()
            .insertInto(ticketTable, ticketTable.ID_SECURE, ticketTable.POKER_ID, ticketTable.NAME)
            .valuesOfRows(tickets.stream().map(r -> row(r.idSecure(), r.pokerId(), r.name())).toList())
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

    public void activate(long ticketId)
    {
        getDSLContext()
            .update(ticketTable)
            .set(ticketTable.ACTIVE, true)
            .where(ticketTable.ID.eq(ticketId))
            .execute();
    }

    public void deactivate(Long ticketId)
    {
        getDSLContext()
            .update(ticketTable)
            .set(ticketTable.ACTIVE, false)
            .where(ticketTable.ID.eq(ticketId))
            .execute();
    }

    public void delete(long ticketId)
    {
        getDSLContext()
            .deleteFrom(ticketTable)
            .where(ticketTable.ID.eq(ticketId))
            .execute();
    }
}
