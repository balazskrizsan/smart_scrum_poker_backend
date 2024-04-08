package com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;

import java.util.Map;
import java.util.UUID;

public record VoteStopResponse(UUID pokerIdSecure, long finishedTicketId, Map<UUID, Vote> votes)
{
}
