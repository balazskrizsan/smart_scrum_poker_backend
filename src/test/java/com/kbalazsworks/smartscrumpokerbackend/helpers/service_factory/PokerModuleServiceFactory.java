package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.PokerRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PokerModuleServiceFactory
{
    @Autowired
    private PokerRepository pokerRepository;

    Map<String, Map<String, Object>> oneTimeMocks = new HashMap<>();

    private <T> T getDependency(Class<?> newClass, Class<T> dependencyClass, T mock, T diObject)
    {
        var newClassFound = oneTimeMocks.getOrDefault(newClass.getName(), null);
        if (newClassFound != null && !newClassFound.isEmpty())
        {
            var dependencyClassFound = (T) newClassFound.getOrDefault(dependencyClass.getName(), null);
            if (dependencyClassFound != null)
            {
                newClassFound.remove(dependencyClass.getName());

                return dependencyClassFound;
            }
        }

        return (T) Optional.ofNullable(mock).orElse(diObject);
    }

    public StartService getStartService()
    {
        return getStartService(null);
    }

    public StartService getStartService(PokerRepository pokerRepositoryMock)
    {
        return new StartService(
            getDependency(StartService.class, PokerRepository.class, pokerRepositoryMock, pokerRepository)
        );
    }
}
