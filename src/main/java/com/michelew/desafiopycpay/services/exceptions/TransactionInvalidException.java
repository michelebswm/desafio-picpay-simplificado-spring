package com.michelew.desafiopycpay.services.exceptions;

public class TransactionInvalidException extends RuntimeException{

    public TransactionInvalidException(String message){
        super(message);
    }
}
