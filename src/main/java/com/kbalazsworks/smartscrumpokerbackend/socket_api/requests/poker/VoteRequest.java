package com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker;

public record VoteRequest(short voteUncertainty, short voteComplexity, short voteEffort)
{
}
