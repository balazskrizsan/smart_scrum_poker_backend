package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.VoteRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoteRequestFakeBuilder
{
    UUID userIdSecure = VoteFakeBuilder.defaultCreatedBy;
    UUID pokerIdSecure =  PokerFakeBuilder.defaultIdSecure1;
    long ticketId = VoteFakeBuilder.defaultTicketId;
    short voteUncertainty = VoteFakeBuilder.defaultUncertainty;
    short voteComplexity = VoteFakeBuilder.defaultComplexity;
    short voteEffort = VoteFakeBuilder.defaultEffort;

    public VoteRequest build()
    {
        return new VoteRequest(
            userIdSecure,
            pokerIdSecure,
            ticketId,
            voteUncertainty,
            voteComplexity,
            voteEffort
        );
    }
}
