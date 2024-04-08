package com.kbalazsworks.smartscrumpokerbackend.helpers.service_factory;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.services.InsecureUserService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.common_module.services.UuidService;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.InGamePlayersRepository;
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
    private CommonServiceFactory commonServiceFactory;
    @Autowired
    private PokerRepository pokerRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private InGamePlayersRepository inGamePlayersRepository;
    @Autowired
    private AccountServiceFactory accountServiceFactory;

    public StartService getStartService()
    {
        return getStartService(null, null, null, null);
    }

    public StartService getStartService(
        PokerService pokerServiceMock,
        InsecureUserService insecureUserServiceMock,
        UuidService uuidServiceMock,
        TicketService ticketServiceMock
    )
    {
        return new StartService(
            getDependency(StartService.class, PokerService.class, pokerServiceMock, getPokerService()),
            getDependency(StartService.class, InsecureUserService.class, insecureUserServiceMock, accountServiceFactory.getInsecureUserService()),
            getDependency(StartService.class, UuidService.class, uuidServiceMock, commonServiceFactory.getUuidService()),
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
        return getTicketService(null, null);
    }

    public TicketService getTicketService(
        TicketRepository ticketRepositoryMock,
        UuidService uuidServiceMock
    )
    {
        return new TicketService(
            getDependency(TicketService.class, UuidService.class, uuidServiceMock, commonServiceFactory.getUuidService()),
            getDependency(TicketService.class, TicketRepository.class, ticketRepositoryMock, ticketRepository)
        );
    }

    public RoundService getRoundService()
    {
        return getRoundService(null, null, null);
    }

    public RoundService getRoundService(
        TicketService ticketServiceMock,
        VoteService voteServiceMock,
        PokerService pokerServiceMock
    )
    {
        return new RoundService(
            getDependency(RoundService.class, TicketService.class, ticketServiceMock, getTicketService()),
            getDependency(RoundService.class, VoteService.class, voteServiceMock, getVoteService()),
            getDependency(RoundService.class, PokerService.class, pokerServiceMock, getPokerService())
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

    public RoomStateService getRoomStateService()
    {
        return getRoomStateService(null, null, null, null, null);
    }

    public RoomStateService getRoomStateService(
        PokerService pokerServiceMock,
        InsecureUserService insecureUserServiceMock,
        InGamePlayersService inGamePlayersServiceMock,
        TicketService ticketServiceMock,
        VoteService voteServiceMock
    )
    {
        return new RoomStateService(
            getDependency(RoomStateService.class, PokerService.class, pokerServiceMock, getPokerService()),
            getDependency(RoomStateService.class, InsecureUserService.class, insecureUserServiceMock, accountServiceFactory.getInsecureUserService()),
            getDependency(RoomStateService.class, InGamePlayersService.class, inGamePlayersServiceMock, getInGamePlayersService()),
            getDependency(RoomStateService.class, TicketService.class, ticketServiceMock, getTicketService()),
            getDependency(RoomStateService.class, VoteService.class, voteServiceMock, getVoteService())
        );
    }

    public InGamePlayersService getInGamePlayersService()
    {
        return getInGamePlayersService(null);
    }

    public InGamePlayersService getInGamePlayersService(InGamePlayersRepository inGamePlayersRepositoryMock)
    {
        return new InGamePlayersService(
            getDependency(InGamePlayersService.class, InGamePlayersRepository.class, inGamePlayersRepositoryMock, inGamePlayersRepository)
        );
    }
}
