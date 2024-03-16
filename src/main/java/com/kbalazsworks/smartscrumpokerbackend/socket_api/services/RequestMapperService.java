package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPoker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;

import java.time.ZoneId;
import java.util.Date;

public class RequestMapperService
{
    public static StartPoker mapToEntity(StartRequest request)
    {
        return new StartPoker(
            new Poker(
                null,
                null,
                request.sprintTitle(),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                ""
            ),
            request.ticketNames().stream().map(tn -> new Ticket(null, tn)).toList()
        );
    }
}
