package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities;

import lombok.NonNull;

import java.time.LocalDateTime;

public record Vote(
    Long id,
    short uncertainty,
    short complexity,
    short effort,
    short calculatedPoint,
    @NonNull LocalDateTime createdAt,
    @NonNull String createdBy
)
{
}
