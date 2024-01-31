package com.sva.testtask.service;

import com.sva.testtask.dto.WalletCreateDto;
import com.sva.testtask.dto.WalletRequestDto;
import com.sva.testtask.dto.WalletResponseDto;
import com.sva.testtask.entity.Wallet;
import com.sva.testtask.exception.AppError;
import com.sva.testtask.exception.NotFoundException;
import com.sva.testtask.exception.ValidationException;
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
            throw new NotFoundException("Такого кошелька не сушествует!");
        }
        return walletOptional.get();
    }

    @Override
    public WalletResponseDto createWallet(WalletCreateDto walletCreateDto) {
        if (walletCreateDto.getAccount() == null) {
            throw new AppError("Значение 'account' не может быть пустым!");
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
        if (walletRequestDto.getAmount() == null) {
            throw new AppError("Значение 'amount' не может быть пустым!");
        }
        long result = wallet.getAccount();
        long amount = walletRequestDto.getAmount();

        switch (validationOperationType(walletRequestDto.getOperationType())) {
            case "DEPOSIT" -> result += amount;
            case "WITHDRAW" -> {
                if (result < amount) {
                    throw new ValidationException("Баланса кошелька недостаточно для списания!");
                } else {
                    result -= amount;
                }
            }
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

    private String validationOperationType(String s) {
        if (!s.equals("DEPOSIT") && !s.equals("WITHDRAW")) {
            throw new ValidationException("Операция должна быть DEPOSIT или WITHDRAW!");
        }

        return s;
    }
}
