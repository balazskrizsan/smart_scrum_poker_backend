package com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.services;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.entities.InGamePlayer;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.poker_module.repositories.InGamePlayersRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InGamePlayersService
{
    InGamePlayersRepository inGamePlayersRepository;

    public void onDuplicateKeyIgnoreAdd(@NonNull InGamePlayer inGamePlayer)
    {
        inGamePlayersRepository.onDuplicateKeyIgnoreAdd(inGamePlayer);
    }

    public List<InGamePlayer> searchUserSecureIdsByPokerIdSecure(UUID pokerIdSecure)
    {
        return inGamePlayersRepository.searchUserSecureIdsByPokerIdSecure(pokerIdSecure);
    }
}
