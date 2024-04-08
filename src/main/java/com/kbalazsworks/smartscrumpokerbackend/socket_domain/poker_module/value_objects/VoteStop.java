package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;

import java.util.Map;
import java.util.UUID;

public record VoteStop(Map<UUID, Vote> votes)
{
}
