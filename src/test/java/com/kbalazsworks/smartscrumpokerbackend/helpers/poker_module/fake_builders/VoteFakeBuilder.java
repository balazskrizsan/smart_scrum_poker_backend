package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class VoteFakeBuilder
{
    public static final long defaultId1 = 103001L;
    public static final long defaultId2 = 103002L;
    public static final long defaultId3 = 103003L;
    public static final long defaultId4 = 103004L;

    private Long id = defaultId1;
    private Long id2 = defaultId2;
    private Long id3 = defaultId3;
    private Long id4 = defaultId4;
    private Long ticketId = TicketFakeBuilder.defaultId1;
    private Long ticketId2 = TicketFakeBuilder.defaultId1;
    private Long ticketId3 = TicketFakeBuilder.defaultId2;
    private Long ticketId4 = TicketFakeBuilder.defaultId2;
    private short uncertainty = 1;
    private short complexity = 2;
    private short effort = 3;
    private short calculatedPoint = 5;
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    private UUID createdBy = InsecureUserFakeBuilder.defaultIdSecure1;
    private UUID createdBy2 = InsecureUserFakeBuilder.defaultIdSecure2;
    private UUID createdBy3 = InsecureUserFakeBuilder.defaultIdSecure1;
    private UUID createdBy4 = InsecureUserFakeBuilder.defaultIdSecure3;

    public Vote build()
    {
        return new Vote(id, ticketId, uncertainty, complexity, effort, calculatedPoint, createdAt, createdBy);
    }

    public Vote build2()
    {
        return new Vote(id2, ticketId2, uncertainty, complexity, effort, calculatedPoint, createdAt, createdBy2);
    }

    public Vote build3()
    {
        return new Vote(id3, ticketId3, uncertainty, complexity, effort, calculatedPoint, createdAt, createdBy3);
    }

    public Vote build4()
    {
        return new Vote(id4, ticketId4, uncertainty, complexity, effort, calculatedPoint, createdAt, createdBy4);
    }
}
