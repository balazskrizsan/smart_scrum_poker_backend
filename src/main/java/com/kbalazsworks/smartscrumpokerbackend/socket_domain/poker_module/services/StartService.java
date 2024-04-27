package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.services.JooqService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPokerResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.Configuration;
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
    private final JooqService jooqService;

    public StartPokerResponse start(@NonNull Poker poker, @NonNull List<Ticket> tickets) throws PokerException, AccountException
    {
        insecureUserService.findByIdSecure(poker.createdBy());

        var newPoker = jooqService.getDbContext().transactionResult(
            (Configuration config) -> transactionalCreate(poker, tickets)
        );

        return new StartPokerResponse(newPoker);
    }

    private Poker transactionalCreate(@NonNull Poker poker, @NonNull List<Ticket> tickets) throws PokerException
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
