package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.account.InsecureUserCreateRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.VoteRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPoker;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

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
                UUID.randomUUID() // @todo: get from UI
            ),
            request.ticketNames().stream().map(tn -> new Ticket(null, null, null, tn, false)).toList()
        );
    }

    public static Vote mapToEntity(VoteRequest voteRequest)
    {
        return new Vote(
            null,
            voteRequest.ticketId(),
            voteRequest.voteUncertainty(),
            voteRequest.voteComplexity(),
            voteRequest.voteEffort(),
            null,
            new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            voteRequest.userIdSecure()
        );
    }

    public static InsecureUser mapToEntity(InsecureUserCreateRequest insecureUserCreateRequest)
    {
        return new InsecureUser(
            null,
            null,
            insecureUserCreateRequest.userName(),
            new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }
}
