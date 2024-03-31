package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities;

import lombok.NonNull;

import java.util.UUID;

public record Ticket(Long id, UUID idSecure, Long pokerId, @NonNull String name, boolean isActive)
{
}
