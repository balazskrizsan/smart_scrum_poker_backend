package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketConnectionHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.SocketNotificationHandlerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.PokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocketApiServiceFactory extends AbstractServiceFactory
{
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final PokerModuleServiceFactory pokerModuleServiceFactory;
    private final AccountServiceFactory accountServiceFactory;

    public NotificationService getNotificationService()
    {
        return getNotificationService(null);
    }

    public SocketConnectionHandlerService getSocketConnectionHandlerService()
    {
        return new SocketConnectionHandlerService();
    }

    public NotificationService getNotificationService(SimpMessagingTemplate simpMessagingTemplateMock)
    {
        return new NotificationService(
            getDependency(NotificationService.class, SimpMessagingTemplate.class, simpMessagingTemplateMock, simpMessagingTemplate)
        );
    }

    public SocketNotificationHandlerService getSocketNotificationHandlerService()
    {
        return getSocketNotificationHandlerService(null, null, null);
    }

    public SocketNotificationHandlerService getSocketNotificationHandlerService(
        PokerService pokerServiceMock,
        NotificationService notificationServiceMock,
        InsecureUserService insecureUserServiceMock
    )
    {
        return new SocketNotificationHandlerService(
            getDependency(SocketNotificationHandlerService.class, PokerService.class, pokerServiceMock, pokerModuleServiceFactory.getPokerService()),
            getDependency(SocketNotificationHandlerService.class, NotificationService.class, notificationServiceMock, getNotificationService()),
            getDependency(SocketNotificationHandlerService.class, InsecureUserService.class, insecureUserServiceMock, accountServiceFactory.getInsecureUserService())
        );
    }
}
