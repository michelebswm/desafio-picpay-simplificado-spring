package com.michelew.desafiopycpay.controllers;

import com.michelew.desafiopycpay.domain.Wallet;
import com.michelew.desafiopycpay.dto.TransactionResponseDTO;
import com.michelew.desafiopycpay.dto.WalletIdResponseDTO;
import com.michelew.desafiopycpay.services.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService service;

    @GetMapping
    public ResponseEntity<List<Wallet>> findAll(){
        List<Wallet> wallets = service.findAll();
        return ResponseEntity.ok().body(wallets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findById(@PathVariable Long id){
        Wallet wallet = service.findById(id);
        return ResponseEntity.ok().body(wallet);
    }


    @PostMapping
    public ResponseEntity<WalletIdResponseDTO> insert(@RequestBody @Valid Wallet walletData){
        Wallet newWallet = service.insert(walletData);
        WalletIdResponseDTO responseDTO = new WalletIdResponseDTO(newWallet.getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newWallet.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }
}
