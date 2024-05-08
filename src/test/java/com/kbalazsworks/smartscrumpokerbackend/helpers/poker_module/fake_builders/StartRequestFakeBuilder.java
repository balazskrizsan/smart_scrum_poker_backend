package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.requests.poker.StartRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StartRequestFakeBuilder
{
    String sprintTitle = PokerFakeBuilder.defaultSprintName;
    List<String> ticketNames = List.of("Test Ticket 1", "Test Ticket 2");
    UUID starterInsecureUserId = InsecureUserFakeBuilder.defaultIdSecure1;

    public StartRequest build()
    {
        return new StartRequest(sprintTitle, ticketNames, starterInsecureUserId);
    }
}
