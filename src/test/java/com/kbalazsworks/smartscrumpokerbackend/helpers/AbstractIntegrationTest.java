package com.kbalazsworks.smartscrumpokerbackend.helpers;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.InGamePlayers;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Vote;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.services.JooqService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractIntegrationTest extends AbstractTest
{
    @Autowired
    private JooqService jooqService;

    protected final Poker pokerTable = Poker.POKER;
    protected final Ticket ticketTable = Ticket.TICKET;
    protected final Vote voteTable = Vote.VOTE;
    protected final InsecureUser insecureUserTable = InsecureUser.INSECURE_USER;
    protected final InGamePlayers inGamePlayersTable = InGamePlayers.IN_GAME_PLAYERS;
    protected final InsecureUserSessions insecureUserSessionsTable = InsecureUserSessions.INSECURE_USER_SESSIONS;

    protected DSLContext getDslContext()
    {
        return jooqService.getDbContext();
    }
}
