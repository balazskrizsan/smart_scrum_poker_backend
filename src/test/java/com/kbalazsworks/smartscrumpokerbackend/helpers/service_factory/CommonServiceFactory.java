package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceFactory extends AbstractServiceFactory
{
    public UuidService getUuidService()
    {
        return new UuidService();
    }
}
