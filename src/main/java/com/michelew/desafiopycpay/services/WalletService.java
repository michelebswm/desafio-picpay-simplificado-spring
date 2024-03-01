package com.michelew.desafiopycpay.services;

import com.michelew.desafiopycpay.domain.User;
import com.michelew.desafiopycpay.domain.Wallet;
import com.michelew.desafiopycpay.repositories.WalletRepository;
import com.michelew.desafiopycpay.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository repository;

    public List<Wallet> findAll(){
        return repository.findAll();
    }

    public Wallet findById(Long id){
        Optional<Wallet> optionalWallet = repository.findById(id);
        return optionalWallet.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Wallet insert(Wallet wallet){
        return repository.save(wallet);
    }
}
