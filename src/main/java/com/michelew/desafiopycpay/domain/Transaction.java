package com.michelew.desafiopycpay.domain;

import com.michelew.desafiopycpay.services.exceptions.TransactionInvalidException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "wallet_id_payer")
    private Wallet payer;

    @OneToOne
    @JoinColumn(name = "wallet_id_receiver")
    private Wallet receiver;

    private Double amount;


}
