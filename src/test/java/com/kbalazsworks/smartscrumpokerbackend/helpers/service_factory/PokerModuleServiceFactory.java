package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.PokerRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.TicketRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.PokerService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.RoundService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.StartService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.TicketService;
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
    @Autowired
    private TicketRepository ticketRepository;

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
        return getStartService(null, null);
    }

    public StartService getStartService(
        PokerService pokerServiceMock,
        TicketService ticketServiceMock
    )
    {
        return new StartService(
            getDependency(StartService.class, PokerService.class, pokerServiceMock, getPokerService()),
            getDependency(TicketRepository.class, TicketService.class, ticketServiceMock, getTicketService())
        );
    }

    public PokerService getPokerService()
    {
        return getPokerService(null);
    }

    public PokerService getPokerService(
        PokerRepository pokerRepositoryMock
    )
    {
        return new PokerService(
            getDependency(PokerService.class, PokerRepository.class, pokerRepositoryMock, pokerRepository)
        );
    }

    public TicketService getTicketService()
    {
        return getTicketService(null);
    }

    public TicketService getTicketService(
        TicketRepository TicketRepositoryMock
    )
    {
        return new TicketService(
            getDependency(TicketService.class, TicketRepository.class, TicketRepositoryMock, ticketRepository)
        );
    }

    public RoundService getRoundService()
    {
        return getRoundService(null);
    }

    public RoundService getRoundService(TicketService TicketServiceMock)
    {
        return new RoundService(
            getDependency(RoundService.class, TicketService.class, TicketServiceMock, getTicketService())
        );
    }
}
