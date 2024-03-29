package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories.InsecureUserRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceFactory extends AbstractServiceFactory
{
    @Autowired
    private InsecureUserRepository insecureUserRepository;

    public InsecureUserService getInsecureUserService()
    {
        return getInsecureUserService(null);
    }

    public InsecureUserService getInsecureUserService(InsecureUserRepository insecureUserRepositoryMock)
    {
        return new InsecureUserService(
            getDependency(InsecureUserService.class, InsecureUserRepository.class, insecureUserRepositoryMock, insecureUserRepository)
        );
    }
}
