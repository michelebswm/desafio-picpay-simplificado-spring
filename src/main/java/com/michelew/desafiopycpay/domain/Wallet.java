package com.michelew.desafiopycpay.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public void withdraw(Double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new RuntimeException("Transaction invalid: Insufficient balance.");
        }
    }

    public void deposit(Double amount){
        this.balance += amount;
    }

}
