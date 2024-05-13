package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.TicketRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketService
{
    UuidService uuidService;
    TicketRepository ticketRepository;

    public void createAll(@NonNull List<Ticket> tickets)
    {
        ticketRepository.createAll(
            tickets.stream()
                .map(t -> new Ticket(null, uuidService.getRandom(), t.pokerId(), t.name(), t.isActive()))
                .toList()
        );
    }

    public List<Ticket> searchByPokerId(@NonNull Long pokerId)
    {
        return ticketRepository.searchByPokerId(pokerId);
    }

    public void activate(long ticketId)
    {
        ticketRepository.activate(ticketId);
    }

    public void deactivate(Long ticketId)
    {
        ticketRepository.deactivate(ticketId);
    }

    public void delete(long ticketId)
    {
        ticketRepository.delete(ticketId);
    }
}
