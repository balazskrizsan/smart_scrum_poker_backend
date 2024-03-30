package com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker;

import java.util.UUID;

public record VoteRequest(UUID userIdSecure, short voteUncertainty, short voteComplexity, short voteEffort)
{
}
