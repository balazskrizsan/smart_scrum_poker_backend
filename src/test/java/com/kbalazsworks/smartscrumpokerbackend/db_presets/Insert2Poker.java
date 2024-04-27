package com.kbalazsworks.smartscrumpokerbackend.db_presets;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import lombok.NonNull;
import org.jooq.DSLContext;

public class Insert2Poker implements IInsert
{
    @Override
    public void runParent()
    {
    }

    @Override
    public void run(@NonNull DSLContext dslContext)
    {
        dslContext.newRecord(Poker.POKER, new PokerFakeBuilder().build()).store();
        dslContext.newRecord(Poker.POKER, new PokerFakeBuilder().build2()).store();
    }
}
