package com.michelew.desafiopycpay.repositories;

import com.michelew.desafiopycpay.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
