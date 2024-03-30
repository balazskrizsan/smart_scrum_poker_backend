package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class VoteFakeBuilder
{
    public static final long defaultId1 = 103001L;

    private Long id = defaultId1;
    private short uncertainty = 1;
    private short complexity = 2;
    private short effort = 3;
    private short calculatedPoint = 5;
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private String createdBy = InsecureUserFakeBuilder.defaultIdSecure1.toString();

    public Vote build()
    {
        return new Vote(id, uncertainty, complexity, effort, calculatedPoint, createdAt, createdBy);
    }
}
