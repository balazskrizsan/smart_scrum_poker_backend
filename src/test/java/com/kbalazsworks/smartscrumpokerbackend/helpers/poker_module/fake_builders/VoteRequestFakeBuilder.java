package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.VoteRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class VoteRequestFakeBuilder
{
    private UUID userIdSecure = VoteFakeBuilder.defaultCreatedBy;
    private UUID pokerIdSecure =  PokerFakeBuilder.defaultIdSecure1;
    private long ticketId = VoteFakeBuilder.defaultTicketId;
    private short voteUncertainty = VoteFakeBuilder.defaultUncertainty;
    private short voteComplexity = VoteFakeBuilder.defaultComplexity;
    private short voteEffort = VoteFakeBuilder.defaultEffort;

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
