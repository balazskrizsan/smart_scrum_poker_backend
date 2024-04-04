package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.account.InsecureUserCreateRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.VoteRequest;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPoker;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RequestMapperService
{
    public static LocalDateTime getNow()
    {
        return new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static StartPoker mapToEntity(@NonNull StartRequest request)
    {
        return new StartPoker(
            new Poker(
                null,
                null,
                request.sprintTitle(),
                getNow(),
                request.starterInsecureUserId()
            ),
            request.ticketNames().stream().map(tn -> new Ticket(null, null, null, tn, false)).toList()
        );
    }

    public static Vote mapToEntity(@NonNull VoteRequest voteRequest)
    {
        return new Vote(
            null,
            voteRequest.ticketId(),
            voteRequest.voteUncertainty(),
            voteRequest.voteComplexity(),
            voteRequest.voteEffort(),
            null,
            getNow(),
            voteRequest.userIdSecure()
        );
    }

    public static InsecureUser mapToEntity(@NonNull InsecureUserCreateRequest insecureUserCreateRequest)
    {
        return new InsecureUser(
            null,
            null,
            insecureUserCreateRequest.userName(),
            getNow()
        );
    }
}
