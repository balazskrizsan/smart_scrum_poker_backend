package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities;

import lombok.NonNull;

public record Ticket(Long id, @NonNull String name)
{
}
