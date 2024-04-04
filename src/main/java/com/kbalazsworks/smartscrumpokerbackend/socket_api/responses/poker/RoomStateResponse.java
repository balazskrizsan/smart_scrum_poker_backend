package com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;

import java.util.List;

public record RoomStateResponse(Poker poker, List<Ticket> tickets, List<InsecureUser> inGameInsecureUsers)
{
}
