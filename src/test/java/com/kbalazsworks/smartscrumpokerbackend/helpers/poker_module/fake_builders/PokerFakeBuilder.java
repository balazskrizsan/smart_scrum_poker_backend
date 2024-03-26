package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;

import java.time.LocalDateTime;
import java.util.UUID;

public class PokerFakeBuilder
{
    public static final Long defaultId1 = 100001L;
    public static final UUID defaultIdSecure1 = UUID.fromString("10000000-0000-0000-0000-000000000001");

    private static final Long id = defaultId1;
    private static final UUID idSecure = defaultIdSecure1;
    private static final String sprintName = "sprint #1";
    private static final LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private static final String createdBy = "creator";

    public Poker buildNoId()
    {
        return new Poker(null, idSecure, sprintName, createdAt, createdBy);
    }
}
