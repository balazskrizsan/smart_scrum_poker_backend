package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.GameStateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameStateRequestFakeBuilder
{
    UUID pokerIdSecure = PokerFakeBuilder.defaultIdSecure1;
    UUID insecureUserId = InsecureUserFakeBuilder.defaultIdSecure1;
    LocalDateTime now = LocalDateTime.of(2020, 11, 22, 11, 22, 33);

    public GameStateRequest build()
    {
        return new GameStateRequest(pokerIdSecure, insecureUserId, now);
    }
}
