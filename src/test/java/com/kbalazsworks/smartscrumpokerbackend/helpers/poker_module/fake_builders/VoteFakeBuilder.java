package com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoteFakeBuilder
{
    public static final long defaultId = 103001L;
    public static final long defaultId2 = 103002L;
    public static final long defaultId3 = 103003L;
    public static final long defaultId4 = 103004L;
    public static final long defaultId5 = 103005L;
    public static final Long defaultTicketId = TicketFakeBuilder.defaultId1;
    public static final UUID defaultCreatedBy = InsecureUserFakeBuilder.defaultIdSecure1;
    public static final UUID defaultCreatedBy2 = InsecureUserFakeBuilder.defaultIdSecure2;
    public static final UUID defaultCreatedBy3 = InsecureUserFakeBuilder.defaultIdSecure1;
    public static final UUID defaultCreatedBy4 = InsecureUserFakeBuilder.defaultIdSecure3;
    public static final UUID defaultCreatedBy5 = InsecureUserFakeBuilder.defaultIdSecure4;
    public static final short defaultUncertainty = 1;
    public static final short defaultComplexity = 2;
    public static final short defaultEffort = 3;
    public static final short defaultCalculatedPoint = 5;
    public static final short defaultCalculatedPoint4 = 13;

    Long id = defaultId;
    Long id2 = defaultId2;
    Long id3 = defaultId3;
    Long id4 = defaultId4;
    Long id5 = defaultId5;
    Long ticketId = TicketFakeBuilder.defaultId1;
    Long ticketId2 = TicketFakeBuilder.defaultId1;
    Long ticketId3 = TicketFakeBuilder.defaultId2;
    Long ticketId4 = TicketFakeBuilder.defaultId2;
    Long ticketId5 = TicketFakeBuilder.defaultId3;
    short uncertainty = defaultUncertainty;
    short complexity = defaultComplexity;
    short effort = defaultEffort;
    short calculatedPoint = defaultCalculatedPoint;
    short calculatedPoint4 = defaultCalculatedPoint4;
    LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);
    UUID createdBy = defaultCreatedBy;
    UUID createdBy2 = defaultCreatedBy2;
    UUID createdBy3 = defaultCreatedBy3;
    UUID createdBy4 = defaultCreatedBy4;
    UUID createdBy5 = defaultCreatedBy5;

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
        return new Vote(id4, ticketId4, uncertainty, complexity, effort, calculatedPoint4, createdAt, createdBy4);
    }

    public Vote build5()
    {
        return new Vote(id5, ticketId5, uncertainty, complexity, effort, calculatedPoint, createdAt, createdBy5);
    }
}
