package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPokerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StartService
{
    private final PokerService pokerService;
    private final InsecureUserService insecureUserService;
    private final UuidService uuidService;
    private final TicketService ticketService;

    public StartPokerResponse start(Poker poker, List<Ticket> tickets) throws PokerException, AccountException
    {
        insecureUserService.findByIdSecure(poker.createdBy());

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

        return new StartPokerResponse(newPoker);
    }
}
