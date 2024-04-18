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
    public static final long defaultId2 = 100002L;
    public static final UUID defaultIdSecure1 = UUID.fromString("10000000-0000-0000-0000-000000000001");
    public static final UUID defaultIdSecure2 = UUID.fromString("10000000-0000-0000-0000-000000000002");
    public static final String defaultSprintName = "sprint #1";
    public static final String defaultSprintName2 = "sprint #2";

    private long id = defaultId1;
    private long id2 = defaultId2;
    private UUID idSecure = defaultIdSecure1;
    private UUID idSecure2 = defaultIdSecure2;
    private String sprintName = defaultSprintName;
    private String sprintName2 = defaultSprintName2;
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private UUID createdBy = InsecureUserFakeBuilder.defaultIdSecure1;
    private UUID createdBy2 = InsecureUserFakeBuilder.defaultIdSecure2;

    public Poker build()
    {
        return new Poker(id, idSecure, sprintName, createdAt, createdBy);
    }

    public Poker build2()
    {
        return new Poker(id2, idSecure2, sprintName2, createdAt, createdBy2);
    }

    public Poker buildNoId()
    {
        return new Poker(null, idSecure, sprintName, createdAt, createdBy);
    }
}
