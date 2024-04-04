package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.RoomStateRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class RoomStateRequestFakeBuilder
{
    private UUID pokerIdSecure = PokerFakeBuilder.defaultIdSecure1;
    private UUID insecureUserId = InsecureUserFakeBuilder.defaultIdSecure1;
    private LocalDateTime now = LocalDateTime.of(2020, 11, 22, 11, 22, 33);

    public RoomStateRequest build()
    {
        return new RoomStateRequest(pokerIdSecure, insecureUserId, now);
    }
}
