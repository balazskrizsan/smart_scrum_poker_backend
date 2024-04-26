package com.kbalazsworks.smartscrumpokerbackend.socket_domain.integration.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.PokerRecord;
import com.kbalazsworks.smartscrumpokerbackend.db_presets.Insert1InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.helpers.AbstractIntegrationTest;
import com.kbalazsworks.smartscrumpokerbackend.helpers.common_module.mocker.UuidServiceMocker;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.PokerFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.helpers.poker_module.fake_builders.TicketFakeBuilder;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.StartService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.TicketService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.StartPokerResponse;
import com.kbalazsworks.smartscrumpokerbackend.test_aspects.SqlPreset;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StartService_StartTest extends AbstractIntegrationTest
{
    @Test
    @SqlPreset(presets = {Insert1InsecureUser.class})
    @SneakyThrows
    public void insertValidStartData_createsPokerInDb()
    {
        // Arrange
        Poker testedPoker = new PokerFakeBuilder().buildNoId();
        List<Ticket> testedTickets = new TicketFakeBuilder().buildNoIdAsList();

        Poker expectedPoker = new PokerFakeBuilder().id(1L).build();
        Ticket expectedTicket = new TicketFakeBuilder().id(1).pokerId(1).build();

        var ticketUuidServiceMock = new UuidServiceMocker().mockGetRandom(TicketFakeBuilder.defaultIdSecure1).getMock();
        setOneTimeMock(TicketService.class, ticketUuidServiceMock);

        var pokerUuidServiceMock = new UuidServiceMocker().mockGetRandom(PokerFakeBuilder.defaultIdSecure1).getMock();

        // Act
        StartPokerResponse actualStartPokerResponse = createInstance(StartService.class, pokerUuidServiceMock)
            .start(testedPoker, testedTickets);

        // Assert
        PokerRecord actualPokerRecord = getDslContext().selectFrom(pokerTable).fetchOne();
        Poker actualPoker = actualPokerRecord.into(Poker.class);

        Ticket actualTicketRow = getDslContext().selectFrom(ticketTable).fetchOne().into(Ticket.class);

        assertAll(
            () -> assertThat(actualStartPokerResponse.poker()).isEqualTo(actualPoker),
            () -> assertThat(actualPoker).isEqualTo(expectedPoker),
            () -> assertThat(actualTicketRow).isEqualTo(expectedTicket)
        );
    }

    @Test
    @SqlPreset()
    @SneakyThrows
    public void insertWithoutDbUser_throwsAccountException()
    {
        // Arrange
        Poker testedPoker = new PokerFakeBuilder().buildNoId();
        List<Ticket> testedTickets = new TicketFakeBuilder().buildNoIdAsList();

        var ticketUuidServiceMock = new UuidServiceMocker().mockGetRandom(TicketFakeBuilder.defaultIdSecure1).getMock();
        setOneTimeMock(TicketService.class, ticketUuidServiceMock);

        var pokerUuidServiceMock = new UuidServiceMocker().mockGetRandom(PokerFakeBuilder.defaultIdSecure1).getMock();

        // Act - Assert
        assertThatThrownBy(() -> createInstance(StartService.class, pokerUuidServiceMock)
            .start(testedPoker, testedTickets)
        )
            .isInstanceOf(AccountException.class)
            .hasMessage("User not found");
    }
}
