package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record Vote(
    Long id,
    Long ticketId,
    short uncertainty,
    short complexity,
    short effort,
    Short calculatedPoint,
    @NonNull LocalDateTime createdAt,
    @NonNull UUID createdBy
)
{
}
