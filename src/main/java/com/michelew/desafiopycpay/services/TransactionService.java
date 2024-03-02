package com.michelew.desafiopycpay.services;

import com.michelew.desafiopycpay.domain.Transaction;
import com.michelew.desafiopycpay.domain.Wallet;
import com.michelew.desafiopycpay.repositories.TransactionRepository;
import com.michelew.desafiopycpay.repositories.WalletRepository;
import com.michelew.desafiopycpay.services.exceptions.ResourceNotFoundException;
import com.michelew.desafiopycpay.services.exceptions.TransactionInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private WalletRepository walletRepository;


    public List<Transaction> findAll(){
        return repository.findAll();
    }

    public Transaction findById(Long id){
        Optional<Transaction> optionalTransaction = repository.findById(id);
        return optionalTransaction.orElseThrow(()-> new ResourceNotFoundException(id));
    }


    public Transaction insert(Transaction transactionData){
        if (!walletRepository.existsById(transactionData.getPayer().getId())){
            throw new ResourceNotFoundException(transactionData.getPayer().getId());
        }
        if (!walletRepository.existsById(transactionData.getReceiver().getId())){
            throw new ResourceNotFoundException(transactionData.getReceiver().getId());
        }
        validateUser(transactionData);
        valideteTypeUserPayer(transactionData);
        validateAmount(transactionData);
        validateBalance(transactionData);

        return repository.save(transactionData);
    }

    public void validateUser(Transaction transaction){
        Wallet payerWallet = walletRepository.findById(transaction.getPayer().getId())
                .orElseThrow(() -> new TransactionInvalidException("Payer wallet not found."));
        Wallet receiverWallet = walletRepository.findById(transaction.getReceiver().getId())
                .orElseThrow(() -> new TransactionInvalidException("Receiver wallet not found."));

        if (payerWallet.getUser() == null || receiverWallet.getUser() == null) {
            throw new TransactionInvalidException("Payer or receiver user is null.");
        }
        if (payerWallet.getUser().getId().equals(receiverWallet.getUser().getId())) {
            throw new TransactionInvalidException("Payer and receiver cannot be the same user.");
        }
    }

    public void valideteTypeUserPayer(Transaction transaction){
        Wallet payerWallet = walletRepository.findById(transaction.getPayer().getId())
                .orElseThrow(() -> new TransactionInvalidException("Payer wallet not found."));
        if (payerWallet.getUser().getTypeUser().getCode() == 2){
            throw new TransactionInvalidException("User type Merchant cannot send money");
        }
    }

    public void validateAmount(Transaction transaction){
        if (transaction.getAmount() == null || transaction.getAmount() <= 0.0){
            throw new TransactionInvalidException("Amount cannot be zero or less than zero");
        }
    }


    public void validateBalance(Transaction transaction){
        Wallet payerWallet = walletRepository.findById(transaction.getPayer().getId())
                .orElseThrow(() -> new TransactionInvalidException("Payer wallet not found."));
        Wallet receiverWallet = walletRepository.findById(transaction.getReceiver().getId())
                .orElseThrow(() -> new TransactionInvalidException("Receiver wallet not found."));

        if (payerWallet.getBalance() < transaction.getAmount()){
            throw new TransactionInvalidException("Transaction invalid: Insufficient balance.");
        }
        payerWallet.withdraw(transaction.getAmount());
        receiverWallet.deposit(transaction.getAmount());
    }
}
