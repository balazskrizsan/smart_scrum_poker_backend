package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_api.services.NotificationService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketApiServiceFactory extends AbstractServiceFactory
{
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public NotificationService getNotificationService()
    {
        return getNotificationService(null);
    }

    public NotificationService getNotificationService(SimpMessagingTemplate simpMessagingTemplateMock)
    {
        return new NotificationService(
            getDependency(StartService.class, SimpMessagingTemplate.class, simpMessagingTemplateMock, simpMessagingTemplate)
        );
    }
}
