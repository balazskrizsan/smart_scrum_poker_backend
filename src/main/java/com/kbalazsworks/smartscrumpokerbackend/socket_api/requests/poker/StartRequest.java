package com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker;

import lombok.NonNull;

import java.util.List;

public record StartRequest(@NonNull String sprintTitle, @NonNull List<String> ticketNames)
{
}
