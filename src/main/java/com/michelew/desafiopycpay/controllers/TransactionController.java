package com.michelew.desafiopycpay.controllers;

import com.michelew.desafiopycpay.domain.Transaction;
import com.michelew.desafiopycpay.dto.TransactionResponseDTO;
import com.michelew.desafiopycpay.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<TransactionResponseDTO> insert(@RequestBody @Valid Transaction transactionData){
        Transaction newTransaction = service.insert(transactionData);
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(newTransaction.getAmount(), newTransaction.getPayer().getId(), newTransaction.getReceiver().getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newTransaction.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }
}
