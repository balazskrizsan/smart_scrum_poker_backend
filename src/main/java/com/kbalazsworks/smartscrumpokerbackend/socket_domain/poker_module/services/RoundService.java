package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoundService
{
    private final TicketService ticketService;

    public void start(UUID pokerIdSecure, long ticketId)
    {
        // @todo: exception if pokerIdSecure not exists
        ticketService.activate(ticketId);
    }

    // @todo: test
    public void stop(UUID pokerIdSecure, Long ticketId)
    {
        // @todo: exception if pokerIdSecure not exists
        ticketService.deactivate(ticketId);
    }
}
