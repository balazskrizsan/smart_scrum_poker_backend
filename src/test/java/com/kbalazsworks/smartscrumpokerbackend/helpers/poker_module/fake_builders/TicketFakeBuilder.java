package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketFakeBuilder
{
    private static final Long id = 101001L;
    private static final Long idSecure = 100001L;
    private static final String name = "sprint #1";

    public List<Ticket> buildNoIdAsList()
    {
        return new ArrayList<>()
        {{
            add(buildNoId());
        }};
    }

    public Ticket buildNoId()
    {
        return new Ticket(null, null, name);
    }
}
