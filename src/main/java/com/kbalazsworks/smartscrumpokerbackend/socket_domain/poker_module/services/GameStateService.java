package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.GameStateResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.InGamePlayer;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Ticket;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Vote;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.value_objects.GameStateRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GameStateService
{
    PokerService pokerService;
    InsecureUserService insecureUserService;
    InGamePlayersService inGamePlayersService;
    TicketService ticketService;
    VoteService voteService;

    public GameStateResponse get(@NonNull GameStateRequest gameStateRequest) throws PokerException, AccountException
    {
        UUID pokerIdSecure = gameStateRequest.pokerIdSecure();
        UUID insecureUserId = gameStateRequest.insecureUserId();

        InsecureUser currentInsecureUser = insecureUserService.findByIdSecure(insecureUserId);
        Poker poker = pokerService.findByIdSecure(pokerIdSecure);

        List<Ticket> tickets = ticketService.searchByPokerId(poker.id());

        inGamePlayersService.onDuplicateKeyIgnoreAdd(
            new InGamePlayer(insecureUserId, pokerIdSecure, gameStateRequest.now())
        );

        List<InGamePlayer> inGamePlayers = inGamePlayersService.searchUserSecureIdsByPokerIdSecure(pokerIdSecure);
        List<UUID> inGameUsersIdSecures = inGamePlayers.stream().map(InGamePlayer::insecureUserIdSecure).toList();

        List<InsecureUser> insecureUsers = insecureUserService.findByIdSecureList(inGameUsersIdSecures);

        Map<Long, Map<UUID, Vote>> votes = voteService
            .getVotesWithTicketGroupByTicketIds(tickets.stream().map(Ticket::id).toList());

        InsecureUser owner = insecureUserService.findByIdSecure(poker.createdBy());

        List<InsecureUser> usersWithSession = insecureUserService.searchUsersWithActiveSession(inGameUsersIdSecures);

        return new GameStateResponse(poker, tickets, insecureUsers, votes, owner, currentInsecureUser, usersWithSession);
    }
}
