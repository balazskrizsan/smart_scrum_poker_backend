package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.InGamePlayer;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class InGamePlayersRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.InGamePlayers inGamePlayersTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.InGamePlayers.IN_GAME_PLAYERS;

    public void onDuplicateKeyAdd(@NonNull InGamePlayer inGamePlayer)
    {
        getDSLContext()
            .insertInto(
                inGamePlayersTable,
                inGamePlayersTable.POKER_ID_SECURE,
                inGamePlayersTable.INSECURE_USER_ID_SECURE,
                inGamePlayersTable.CREATED_AT
            )
            .values(
                inGamePlayer.pokerIdSecure(),
                inGamePlayer.insecureUserIdSecure(),
                inGamePlayer.createdAt()
            )
            .execute();
    }
}
