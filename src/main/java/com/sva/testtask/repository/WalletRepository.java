package com.sva.testtask.repository;

import com.sva.testtask.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    @Override
    Optional<Wallet> findById(UUID uuid);
}
