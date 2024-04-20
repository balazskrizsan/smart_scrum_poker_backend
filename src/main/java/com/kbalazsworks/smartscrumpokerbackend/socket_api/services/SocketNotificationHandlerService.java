package com.kbalazsworks.smartscrumpokerbackend.socket_api.services;

import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.enums.SocketDestination;
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

    public void notifyPokerRoomsWithNewSession(@NonNull UUID insecureUserIdSecure) throws AccountException
    {
        notifyPokerRoom(
            insecureUserIdSecure,
            SESSION_CREATED_OR_UPDATED,
            "Notify poker rooms closed session: {}, pokers: {}"
        );
    }

    // @todo test
    public void notifyPokerRoomsWithLeavingSession(@NonNull UUID insecureUserIdSecure) throws AccountException
    {
        notifyPokerRoom(
            insecureUserIdSecure,
            SESSION_CLOSED,
            "Notify poker rooms with new session: {}, pokers: {}"
        );
    }

    private void notifyPokerRoom(
        @NonNull UUID insecureUserIdSecure,
        @NonNull SocketDestination socketDestination,
        @NonNull String logMessage
    )
        throws AccountException
    {
        InsecureUser user = insecureUserService.findByIdSecure(insecureUserIdSecure);
        Map<UUID, Poker> pokers = pokerService.searchWatchedPokers(insecureUserIdSecure);
        log.info(logMessage, insecureUserIdSecure, pokers.keySet());

        pokers.keySet().forEach(pokerIdSecure -> {
            try
            {
                notificationService.notifyPokerRoom(pokerIdSecure, new SessionResponse(user), socketDestination);
            }
            catch (ApiException e)
            {
                log.error(STR."NotifyPokerRooms error: \{e.getMessage()}", e);
            }
        });
    }
}
