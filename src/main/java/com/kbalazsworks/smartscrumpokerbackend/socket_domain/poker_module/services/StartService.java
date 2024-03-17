package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.PokerRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartService
{
    private final PokerRepository pokerRepository;
    private final TicketRepository ticketRepository;

    public void start(Poker poker, List<Ticket> tickets) throws PokerException
    {
        UUID secureId = UUID.randomUUID();

        Long pokerId = pokerRepository.create(new Poker(
            null,
            secureId,
            poker.name(),
            poker.createdAt(),
            poker.createdBy()
        ));

        ticketRepository.createAll(
            tickets.stream().map(t -> new Ticket(null, pokerId, t.name())).toList()
        );
    }
}
