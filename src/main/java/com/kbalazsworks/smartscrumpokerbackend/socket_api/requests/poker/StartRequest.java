package com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker;

import java.util.List;

public record StartRequest(String sprintTitle, List<String> ticketNames)
{
}
