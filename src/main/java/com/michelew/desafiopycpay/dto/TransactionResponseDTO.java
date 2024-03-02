package com.michelew.desafiopycpay.dto;

public record TransactionResponseDTO(
        Double amount,
        Long payer,
        Long receiver
) {
}
