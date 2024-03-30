package com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories.InsecureUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InsecureUserService
{
    private final InsecureUserRepository insecureUserRepository;

    public InsecureUser create(InsecureUser insecureUser) throws AccountException
    {
        var newUuid = UUID.randomUUID();

        return insecureUserRepository.create(new InsecureUser(
            null,
            newUuid,
            insecureUser.userName(),
            insecureUser.createdAt()
        ));
    }

    public InsecureUser findByIdSecure(String idSecure) throws AccountException
    {
        return insecureUserRepository.findByIdSecure(idSecure);
    }
}
