package com.kbalazsworks.smartscrumpokerbackend.helpers;

import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import org.opentest4j.MultipleFailuresError;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomAsserts
{

    public static String UuidPattern = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";

    public static void softPokerAssert(Poker actual, Poker expected) throws MultipleFailuresError
    {
        assertAll(
            () -> assertThat(actual.id()).isEqualTo(expected.id()),
            () -> assertTrue(actual.idSecure().toString().matches(UuidPattern)),
            () -> assertThat(actual.name()).isEqualTo(expected.name()),
            () -> assertThat(actual.createdBy()).isEqualTo(InsecureUserFakeBuilder.defaultIdSecure1)
        );
    }

    public static void softTicketAssert(Ticket actual, Ticket expected) throws MultipleFailuresError
    {
        assertAll(
            () -> assertThat(actual.id()).isEqualTo(expected.id()),
            () -> assertTrue(actual.idSecure().toString().matches(UuidPattern)),
            () -> assertThat(actual.pokerId()).isEqualTo(expected.pokerId()),
            () -> assertThat(actual.name()).isEqualTo(expected.name()),
            () -> assertThat(actual.isActive()).isEqualTo(expected.isActive())
        );
    }
}
