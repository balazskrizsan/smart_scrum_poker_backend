package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StartService
{
    private final PokerService pokerService;
    private final UuidService uuidService;
    private final TicketService ticketService;

    public Poker start(Poker poker, List<Ticket> tickets) throws PokerException
    {
        Poker newPoker = pokerService.create(new Poker(
            null,
            uuidService.getRandom(),
            poker.name(),
            poker.createdAt(),
            poker.createdBy()
        ));

        ticketService.createAll(
            tickets.stream()
                .map(t -> new Ticket(null, null, newPoker.id(), t.name(), t.isActive()))
                .toList()
        );

        return newPoker;
    }
}
