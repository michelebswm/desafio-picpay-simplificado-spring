package com.michelew.desafiopycpay.repositories;

import com.michelew.desafiopycpay.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
