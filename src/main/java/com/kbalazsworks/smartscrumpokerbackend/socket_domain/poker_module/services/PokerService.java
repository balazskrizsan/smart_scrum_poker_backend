package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.Poker;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.exceptions.PokerException;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.PokerRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PokerService
{
    PokerRepository pokerRepository;

    // @todo: unit test
    public Poker create(@NonNull Poker poker) throws PokerException
    {
        return pokerRepository.create(poker);
    }

    public Poker findByIdSecure(@NonNull UUID pokerIdSecure) throws PokerException
    {
        return pokerRepository.findByIdSecure(pokerIdSecure);
    }

    public Map<UUID, Poker> searchWatchedPokers(@NonNull UUID insecureUserIdSecure)
    {
        return pokerRepository.searchWatchedPokers(insecureUserIdSecure);
    }
}
