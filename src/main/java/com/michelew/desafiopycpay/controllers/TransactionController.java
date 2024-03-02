package com.michelew.desafiopycpay.controllers;

import com.michelew.desafiopycpay.domain.Transaction;
import com.michelew.desafiopycpay.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll(){
        List<Transaction> transactions = service.findAll();
        return ResponseEntity.ok().body(transactions);
    }

    @PostMapping
    public ResponseEntity<Transaction> insert(@RequestBody @Valid Transaction transactionData){
        Transaction newTransaction = service.insert(transactionData);
        return ResponseEntity.ok().body(newTransaction);
    }
}
