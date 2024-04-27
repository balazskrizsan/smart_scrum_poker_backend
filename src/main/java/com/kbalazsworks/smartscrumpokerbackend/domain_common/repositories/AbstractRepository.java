package com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.InGamePlayers;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Vote;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.services.JooqService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
abstract public class AbstractRepository
{
    private JooqService jooqService;

    protected final InsecureUser insecureUserTable = InsecureUser.INSECURE_USER;
    protected final InsecureUserSessions insecureUserSessionsTable = InsecureUserSessions.INSECURE_USER_SESSIONS;
    protected final InGamePlayers inGamePlayersTable = InGamePlayers.IN_GAME_PLAYERS;
    protected final Vote voteTable = Vote.VOTE;
    protected final Ticket ticketTable = Ticket.TICKET;
    protected final Poker pokersTable = Poker.POKER;

    @Autowired
    public void setJooqService(JooqService jooqService)
    {
        this.jooqService = jooqService;
    }

    protected DSLContext getDSLContext()
    {
        return jooqService.getDbContext();
    }
}
