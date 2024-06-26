package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record Poker(
    Long id,
    UUID idSecure,
    @NonNull String name,
    @NonNull LocalDateTime createdAt,
    @NonNull UUID createdBy
)
{
}
