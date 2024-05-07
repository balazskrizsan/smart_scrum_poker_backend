package com.kbalazsworks.smartscrumpokerbackend.socket_api.enums;

public enum SocketDestination
{
    SESSION_CREATED_OR_UPDATED("/app/session.created_or_updated"),
    SESSION_CLOSED("/app/session.closed"),
    POKER_START("/app/poker/start"),
    POKER_ROOM_STATE("/app/poker/room.state"),
    POKER_ROUND_START("/app/poker/vote.start"),
    POKER_TICKET_CLOSED("/app/poker.ticket.closed"),
    POKER_ROUND_STOP("/app/poker/vote.stop"),
    SEND_POKER_VOTE("/app/poker/vote"),
    SEND_POKER_VOTE_NEW_JOINER("/app/poker/vote.new_joiner"),
    SEND_ACCOUNT_INSECURE_USER_CREATE("/app/account.insecure.user.create");

    private final String destination;

    public String getValue()
    {
        return destination;
    }

    SocketDestination(String destination)
    {
        this.destination = destination;
    }
}
