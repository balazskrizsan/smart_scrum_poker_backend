package com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VotesWithVoteStat;

import java.util.UUID;

public record VoteStopResponse(UUID pokerIdSecure, long finishedTicketId, VotesWithVoteStat voteResult)
{
}
