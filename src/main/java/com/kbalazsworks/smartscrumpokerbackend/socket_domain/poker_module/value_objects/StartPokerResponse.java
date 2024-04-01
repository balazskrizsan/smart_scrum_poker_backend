package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;

public record StartPokerResponse(Poker poker, InsecureUser insecureUser)
{
}
