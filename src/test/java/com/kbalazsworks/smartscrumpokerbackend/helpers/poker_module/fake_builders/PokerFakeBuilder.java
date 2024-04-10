package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class PokerFakeBuilder
{
    public static final long defaultId1 = 100001L;
    public static final UUID defaultIdSecure1 = UUID.fromString("10000000-0000-0000-0000-000000000001");
    public static final String defaultSprintName = "sprint #1";

    private long id = defaultId1;
    private UUID idSecure = defaultIdSecure1;
    private String sprintName = defaultSprintName;
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private UUID createdBy = InsecureUserFakeBuilder.defaultIdSecure1;

    public Poker build()
    {
        return new Poker(id, idSecure, sprintName, createdAt, createdBy);
    }

    public Poker buildNoId()
    {
        return new Poker(null, idSecure, sprintName, createdAt, createdBy);
    }
}
