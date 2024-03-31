package com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidService
{
    public UUID getRandom()
    {
        return UUID.randomUUID();
    }
}
