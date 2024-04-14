package com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories.InsecureUserSessionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsecureUserSessionsService
{
    private final InsecureUserSessionsRepository insecureUserSessionsRepository;

    public void add(InsecureUserSession insecureUserSession)
    {
        insecureUserSessionsRepository.create(insecureUserSession);
    }
}
