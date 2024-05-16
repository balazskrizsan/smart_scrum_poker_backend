package com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.VotesWithVoteStat;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record GameStateResponse(
    Poker poker,
    List<Ticket> tickets,
    List<InsecureUser> inGameInsecureUsers,
    Map<Long, Map<UUID, Vote>> votes, // @todo: remove
    InsecureUser owner,
    InsecureUser currentInsecureUser,
    List<InsecureUser> inGameInsecureUsersWithSession,
    Map<Long, VotesWithVoteStat> votesWithVoteStatList
)
{
}
