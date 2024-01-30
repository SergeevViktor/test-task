package com.sva.testtask.service;

import com.sva.testtask.dto.WalletCreateDto;
import com.sva.testtask.dto.WalletRequestDto;
import com.sva.testtask.dto.WalletResponseDto;
import com.sva.testtask.entity.Wallet;
import com.sva.testtask.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    public Wallet getWallet(UUID uuid) {
        Optional<Wallet> walletOptional = walletRepository.findById(uuid);
        if (walletOptional.isEmpty()) {
            //throw new
        }
        return walletOptional.get();
    }

    @Override
    public WalletResponseDto createWallet(WalletCreateDto walletCreateDto) {
        if (walletCreateDto == null) {
            //throw new
        }
        UUID uuid = UUID.randomUUID();
        Wallet wallet = Wallet.builder()
                .id(uuid)
                .account(walletCreateDto.getAccount())
                .build();

        walletRepository.save(wallet);

        return WalletResponseDto.builder()
                .id(wallet.getId())
                .account(wallet.getAccount())
                .build();
    }

    @Override
    public void updateWallet(WalletRequestDto walletRequestDto) {
        Wallet wallet = getWallet(walletRequestDto.getId());
        long result = wallet.getAccount();
        if (result > walletRequestDto.getAmount()) {
            //throw new
        }

        switch (walletRequestDto.getOperationType().toLowerCase()) {
            case "deposit" -> result += walletRequestDto.getAmount();
            case "withdraw" -> result -= walletRequestDto.getAmount();
        }
        wallet.setAccount(result);

        walletRepository.save(wallet);
    }

    @Override
    public WalletResponseDto getWalletBalance(UUID uuid) {
        Wallet wallet = getWallet(uuid);

        return WalletResponseDto.builder()
                .id(wallet.getId())
                .account(wallet.getAccount())
                .build();
    }
}
