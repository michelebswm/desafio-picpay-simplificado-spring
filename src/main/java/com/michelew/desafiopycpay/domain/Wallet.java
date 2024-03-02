package com.michelew.desafiopycpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michelew.desafiopycpay.services.exceptions.TransactionInvalidException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_wallet")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "payer")
    private List<Transaction> payerTransactions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Transaction> receiverTransactions = new ArrayList<>();

    public Wallet(Long id, String account, User user, Double balance) {
        this.id = id;
        this.account = account;
        this.user = user;
        this.balance = balance;
    }

    public void withdraw(Double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new TransactionInvalidException("Transaction invalid: Insufficient balance.");
        }
    }

    public void deposit(Double amount){
        this.balance += amount;
    }

}
