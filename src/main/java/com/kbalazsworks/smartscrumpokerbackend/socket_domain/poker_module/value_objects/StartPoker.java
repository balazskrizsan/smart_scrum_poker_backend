package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import lombok.NonNull;

import java.util.List;

public record StartPoker(@NonNull Poker poker, @NonNull List<Ticket> tickets)
{
}
