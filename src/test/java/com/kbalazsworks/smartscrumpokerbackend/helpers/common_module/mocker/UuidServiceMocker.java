package com.kbalazsworks.smartscrumpokerbackend.helpers.common_module.mocker;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UuidServiceMocker
{
    private final UuidService uuidService;

    public UuidServiceMocker()
    {
        uuidService = mock(UuidService.class);
    }

    public UuidServiceMocker mockGetRandom(UUID mockValue)
    {
        when(uuidService.getRandom()).thenReturn(mockValue);

        return this;
    }

    public UuidService getMock()
    {
        return uuidService;
    }
}
