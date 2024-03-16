package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.PokerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartService
{
    private final PokerRepository pokerRepository;

    public void start(Poker poker, List<Ticket> tickets)
    {
        pokerRepository.create(new Poker(
            null,
            UUID.randomUUID(),
            poker.name(),
            poker.createdAt(),
            poker.createdBy()
        ));
    }
}
