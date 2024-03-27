package com.kbalazsworks.smartscrumpokerbackend.socket_api.enums;

public enum SocketDestination
{
    POKER_START("/app/poker.start"),
    POKER_ROOM_STATE("/app/poker.room.state"),
    POKER_ROUND_START("/app/poker.round.start"),
    POKER_ROUND_STOP("/app/poker.round.stop"),
    SEND_POKER_VOTE("/app/poker.vote");

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
