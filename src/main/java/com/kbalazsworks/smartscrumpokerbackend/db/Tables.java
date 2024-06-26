/*
 * This file is generated by jOOQ.
 */
package com.kbalazsworks.smartscrumpokerbackend.db;


import com.kbalazsworks.smartscrumpokerbackend.db.tables.FlywaySchemaHistory;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.InGamePlayers;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.db.tables.Vote;


/**
 * Convenience access to all tables in public
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>public.flyway_schema_history</code>.
     */
    public static final FlywaySchemaHistory FLYWAY_SCHEMA_HISTORY = FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY;

    /**
     * The table <code>public.in_game_players</code>.
     */
    public static final InGamePlayers IN_GAME_PLAYERS = InGamePlayers.IN_GAME_PLAYERS;

    /**
     * The table <code>public.insecure_user</code>.
     */
    public static final InsecureUser INSECURE_USER = InsecureUser.INSECURE_USER;

    /**
     * The table <code>public.insecure_user_sessions</code>.
     */
    public static final InsecureUserSessions INSECURE_USER_SESSIONS = InsecureUserSessions.INSECURE_USER_SESSIONS;

    /**
     * The table <code>public.poker</code>.
     */
    public static final Poker POKER = Poker.POKER;

    /**
     * The table <code>public.ticket</code>.
     */
    public static final Ticket TICKET = Ticket.TICKET;

    /**
     * The table <code>public.vote</code>.
     */
    public static final Vote VOTE = Vote.VOTE;
}
