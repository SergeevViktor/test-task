package com.sva.testtask.service;

import com.sva.testtask.dto.WalletCreateDto;
import com.sva.testtask.dto.WalletRequestDto;
import com.sva.testtask.dto.WalletResponseDto;
import com.sva.testtask.entity.Wallet;

import java.util.UUID;

public interface WalletService {

    Wallet getWallet(UUID uuid);

    WalletResponseDto createWallet(WalletCreateDto walletCreateDto);

    void updateWallet(WalletRequestDto walletRequestDto);

    WalletResponseDto getWalletBalance(UUID uuid);
}
