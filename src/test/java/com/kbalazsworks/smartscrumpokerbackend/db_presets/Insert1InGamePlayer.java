package com.kbalazsworks.smartscrumpokerbackend.db_presets;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.InGamePlayers;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.InGamePlayerFakeBuilder;
import org.jooq.DSLContext;

public class Insert1InGamePlayer implements IInsert
{
    @Override
    public void runParent()
    {
    }

    @Override
    public void run(DSLContext dslContext)
    {
        dslContext.newRecord(InGamePlayers.IN_GAME_PLAYERS, new InGamePlayerFakeBuilder().build()).store();
    }
}
