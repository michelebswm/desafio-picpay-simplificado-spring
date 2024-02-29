package com.michelew.desafiopycpay.services;

import com.michelew.desafiopycpay.domain.Wallet;
import com.michelew.desafiopycpay.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;

    public List<Wallet> findAll(){
        return repository.findAll();
    }

    public Wallet insert(Wallet wallet){
        return repository.save(wallet);
    }
}
