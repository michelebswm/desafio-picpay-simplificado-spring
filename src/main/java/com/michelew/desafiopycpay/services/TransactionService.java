package com.michelew.desafiopycpay.services;

import com.michelew.desafiopycpay.domain.Transaction;
import com.michelew.desafiopycpay.domain.User;
import com.michelew.desafiopycpay.domain.Wallet;
import com.michelew.desafiopycpay.repositories.TransactionRepository;
import com.michelew.desafiopycpay.repositories.WalletRepository;
import com.michelew.desafiopycpay.services.exceptions.ResourceNotFoundException;
import com.michelew.desafiopycpay.services.exceptions.TransactionInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;


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
        Wallet payer = walletService.findById(transactionData.getPayer().getId());
        User sender = userService.findById(payer.getUser().getId());

        Wallet receiver = walletService.findById(transactionData.getReceiver().getId());
        User receive = userService.findById(receiver.getUser().getId());

        validateUser(transactionData);
        valideteTypeUserPayer(transactionData);
        validateAmount(transactionData);
        validateBalance(transactionData);

        if (!authorizeTransaction(transactionData.getPayer().getUser(), transactionData.getAmount())){
            throw new TransactionInvalidException("Transação não autorizada");
        }

        notificationService.sendNotification(sender, "Transação realizada com sucesso.");
        notificationService.sendNotification(receive, "Transação recebida com sucesso.");

        return repository.save(transactionData);
    }

    public boolean authorizeTransaction(User payer, Double amount){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else{
            return false;
        }
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
