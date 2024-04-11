package com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;

// @todo: startedInsecureUser in Poker.createdBy
public record StartResponse(Poker poker, InsecureUser startedInsecureUser, String requestDestination)
{
}
