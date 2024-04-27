package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.InGamePlayer;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class InGamePlayersRepository extends AbstractRepository
{
    public void onDuplicateKeyIgnoreAdd(@NonNull InGamePlayer inGamePlayer)
    {
        var ctx = getDSLContext();
        ctx.insertInto(inGamePlayersTable)
            .set(ctx.newRecord(inGamePlayersTable, inGamePlayer))
            .onDuplicateKeyIgnore()
            .execute();
    }

    public List<InGamePlayer> searchUserSecureIdsByPokerIdSecure(UUID pokerIdSecure)
    {
        return getDSLContext()
            .selectFrom(inGamePlayersTable)
            .where(inGamePlayersTable.POKER_ID_SECURE.eq(pokerIdSecure))
            .fetchInto(InGamePlayer.class);
    }
}
