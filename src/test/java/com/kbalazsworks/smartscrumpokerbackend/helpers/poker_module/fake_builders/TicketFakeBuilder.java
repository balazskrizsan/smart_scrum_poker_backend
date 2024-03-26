package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(fluent = true)
@Getter
@Setter
public class TicketFakeBuilder
{
    public static final long defaultId1 = 101001L;
    public static final long defaultId2 = 101002L;
    public static final long defaultId3 = 101003L;

    private long id = defaultId1;
    private long id2 = defaultId2;
    private long id3 = defaultId3;
    private long pokerId = PokerFakeBuilder.defaultId1;
    private String name = "ticket #1";
    private String name2 = "ticket #2";
    private String name3 = "ticket #3";
    private boolean isActive = false;
    private boolean isActive2 = false;
    private boolean isActive3 = false;

    public List<Ticket> buildNoIdAsList()
    {
        return new ArrayList<>()
        {{
            add(buildNoId());
        }};
    }

    public Ticket build()
    {
        return new Ticket(id, pokerId, name, isActive);
    }

    public Ticket build2()
    {
        return new Ticket(id2, pokerId, name2, isActive2);
    }

    public Ticket build3()
    {
        return new Ticket(id3, pokerId, name3, isActive3);
    }

    public Ticket buildNoId()
    {
        return new Ticket(null, null, name, isActive);
    }
}
