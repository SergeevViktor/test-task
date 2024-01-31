package com.sva.testtask.controller;

import com.sva.testtask.dto.WalletCreateDto;
import com.sva.testtask.dto.WalletRequestDto;
import com.sva.testtask.dto.WalletResponseDto;
import com.sva.testtask.exception.ValidationException;
import com.sva.testtask.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/api/v1/create-wallet")
    public ResponseEntity<WalletResponseDto> createWallet(@RequestBody WalletCreateDto walletCreateDto) {
        URI uri = URI.create("http://localhost:8081//api/v1/create-wallet");
        return ResponseEntity.created(uri).body(walletService.createWallet(walletCreateDto));
    }

    @PostMapping("/api/v1/wallet")
    public ResponseEntity<Void> updateWallet(@RequestBody WalletRequestDto walletRequestDto) {
        walletService.updateWallet(walletRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/v1/wallets/{walletUuid}")
    public ResponseEntity<WalletResponseDto> getWalletBalance(@PathVariable String walletUuid) {
        UUID uuid;
        try {
            uuid = UUID.fromString(walletUuid);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Невалидный UUID!");
        }
        return ResponseEntity.ok().body(walletService.getWalletBalance(uuid));
    }
}
