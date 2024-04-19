package com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.value_objects;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;

public record DisconnectResponse(boolean shouldSendNotification, InsecureUserSession insecureUserSession)
{
}
