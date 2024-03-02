package com.michelew.desafiopycpay.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.michelew.desafiopycpay.dto.TransactionResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant moment = Instant.now();

    @ManyToOne
    @JoinColumn(name = "wallet_id_payer", nullable = false)
    private Wallet payer;

    @ManyToOne
    @JoinColumn(name = "wallet_id_receiver", nullable = false)
    private Wallet receiver;

    private Double amount;


}
