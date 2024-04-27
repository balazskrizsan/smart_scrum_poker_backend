package com.kbalazsworks.smartscrumpokerbackend.db_presets;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import lombok.NonNull;
import org.jooq.DSLContext;

public class Insert3TicketsAllInactive implements IInsert
{
    @Override
    public void runParent()
    {
    }

    @Override
    public void run(@NonNull DSLContext dslContext)
    {
        dslContext.newRecord(Ticket.TICKET, new TicketFakeBuilder().build()).store();
        dslContext.newRecord(Ticket.TICKET, new TicketFakeBuilder().build2()).store();
        dslContext.newRecord(Ticket.TICKET, new TicketFakeBuilder().build3()).store();
    }
}
