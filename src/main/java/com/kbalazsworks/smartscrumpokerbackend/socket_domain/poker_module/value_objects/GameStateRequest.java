package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameStateRequest(@NonNull UUID pokerIdSecure, @NonNull UUID insecureUserId, @NonNull LocalDateTime now)
{
}
