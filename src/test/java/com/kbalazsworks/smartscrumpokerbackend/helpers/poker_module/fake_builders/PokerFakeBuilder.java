package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;

import java.time.LocalDateTime;
import java.util.UUID;

public class PokerFakeBuilder
{
    private static final Long id = 100001L;
    private static final UUID idSecure = null;
    private static final String sprintName = "sprint #1";
    private static final LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private static final String createdBy = "asd";

    public Poker buildNoId()
    {
        return new Poker(null, idSecure, sprintName, createdAt, createdBy);
    }
}
