package com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker;

import java.util.UUID;

public record VoteRequest(
    UUID userIdSecure,
    UUID pokerIdSecure,
    long ticketId,
    short voteUncertainty,
    short voteComplexity, short voteEffort)
{
}
