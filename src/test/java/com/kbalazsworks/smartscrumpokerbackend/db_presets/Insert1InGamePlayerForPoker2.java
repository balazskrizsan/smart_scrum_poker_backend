package com.kbalazsworks.smartscrumpokerbackend.db_presets;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.InGamePlayers;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.InGamePlayerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import lombok.NonNull;
import org.jooq.DSLContext;

public class Insert1InGamePlayerForPoker2 implements IInsert
{
    @Override
    public void runParent()
    {
    }

    @Override
    public void run(@NonNull DSLContext dslContext)
    {
        dslContext.newRecord(
            InGamePlayers.IN_GAME_PLAYERS,
            new InGamePlayerFakeBuilder().pokerIdSecure(PokerFakeBuilder.defaultIdSecure2).build()
        ).store();
    }
}
