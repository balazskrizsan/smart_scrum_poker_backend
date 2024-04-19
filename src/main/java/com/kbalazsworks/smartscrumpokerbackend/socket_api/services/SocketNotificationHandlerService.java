package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.responses.poker.SessionResponse;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.PokerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.SESSION_CLOSED;
import static com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination.SESSION_CREATED_OR_UPDATED;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketNotificationHandlerService
{
    private final PokerService pokerService;
    private final NotificationService notificationService;
    private final InsecureUserService insecureUserService;

    // @todo test
    public void notifyPokerRoomsWithNewSession(@NonNull UUID insecureUserIdSecure)
        throws AccountException
    {
        // @todo: move to domain
        InsecureUser user = insecureUserService.findByIdSecure(insecureUserIdSecure);
        Map<UUID, Poker> pokers = pokerService.searchWatchedPokers(insecureUserIdSecure);
        log.info("Notify poker rooms with new session: {}, pokers: {}", insecureUserIdSecure, pokers.keySet());
        // @todo: end move to domain

        pokers.keySet().forEach(pokerIdSecure -> {
            try
            {
                notificationService.notifyPokerRoom(pokerIdSecure, new SessionResponse(user), SESSION_CREATED_OR_UPDATED);
            }
            catch (ApiException e)
            {
                throw new RuntimeException(STR."NotifyPokerRooms error: \{e.getMessage()}", e);
            }
        });
    }

    // @todo test
    public void notifyPokerRoomsWithLeavingSession(UUID insecureUserIdSecure)
        throws AccountException
    {
        // @todo: move to domain
        InsecureUser user = insecureUserService.findByIdSecure(insecureUserIdSecure);
        Map<UUID, Poker> pokers = pokerService.searchWatchedPokers(insecureUserIdSecure);
        log.info("Notify poker rooms closed session: {}, pokers: {}", insecureUserIdSecure, pokers.keySet());
        // @todo: end move to domain

        for (UUID pokerIdSecure : pokers.keySet())
        {
            try
            {
                notificationService.notifyPokerRoom(pokerIdSecure, new SessionResponse(user), SESSION_CLOSED);
            }
            catch (ApiException e)
            {
                throw new RuntimeException(STR."NotifyPokerRooms error: \{e.getMessage()}", e);
            }
        }
    }
}
