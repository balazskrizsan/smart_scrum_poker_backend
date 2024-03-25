package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.RoomStateResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.PokerRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomStateService
{
    private final PokerRepository pokerRepository;
    private final TicketRepository ticketRepository;

    public RoomStateResponse get(UUID pokerIdSecure)
    {
        Poker poker = pokerRepository.findByIdSecure(pokerIdSecure);
        List<Ticket> tickets = ticketRepository.searchByPokerId(poker.id());

        return new RoomStateResponse(poker, tickets);
    }
}
