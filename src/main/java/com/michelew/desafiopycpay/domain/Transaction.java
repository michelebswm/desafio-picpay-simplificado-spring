package com.michelew.desafiopycpay.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private User payer;
//
//    private User receiver;

    private Double amount;
}
