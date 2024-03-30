package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.PokerRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.TicketRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.VoteRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PokerModuleServiceFactory extends AbstractServiceFactory
{
    @Autowired
    private PokerRepository pokerRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AccountServiceFactory accountServiceFactory;

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

    public RoundService getRoundService(TicketService ticketServiceMock)
    {
        return new RoundService(
            getDependency(RoundService.class, TicketService.class, ticketServiceMock, getTicketService())
        );
    }

    public VoteService getVoteService()
    {
        return getVoteService(null, null, null);
    }

    public VoteService getVoteService(
        InsecureUserService insecureUserServiceMock,
        StoryPointCalculatorService storyPointCalculatorServiceMock,
        VoteRepository voteRepositoryMock
    )
    {
        return new VoteService(
            getDependency(VoteService.class, InsecureUserService.class, insecureUserServiceMock, accountServiceFactory.getInsecureUserService()),
            getDependency(VoteService.class, StoryPointCalculatorService.class, storyPointCalculatorServiceMock, getStoryPointCalculatorService()),
            getDependency(VoteService.class, VoteRepository.class, voteRepositoryMock, voteRepository)
        );
    }

    public StoryPointCalculatorService getStoryPointCalculatorService()
    {
        return new StoryPointCalculatorService();
    }
}
