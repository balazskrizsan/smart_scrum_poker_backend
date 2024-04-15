package com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories.InsecureUserSessionsRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InsecureUserSessionsService
{
    private final InsecureUserSessionsRepository insecureUserSessionsRepository;

    public boolean add(@NonNull InsecureUserSession insecureUserSession)
    {
        return insecureUserSessionsRepository.create(insecureUserSession);
    }

    public void removeBySessionId(@NonNull UUID sessionId)
    {
        insecureUserSessionsRepository.removeBySessionId(sessionId);
    }
}
