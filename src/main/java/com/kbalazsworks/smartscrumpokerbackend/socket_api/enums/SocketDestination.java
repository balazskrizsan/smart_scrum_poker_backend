package com.kbalazsworks.smartscrumpokerbackend.socket_api.enums;

public enum SocketDestination
{
    POKER_START("/app/poker.start"),
    POKER_ROOM_STATE("/app/poker.room.state");

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
