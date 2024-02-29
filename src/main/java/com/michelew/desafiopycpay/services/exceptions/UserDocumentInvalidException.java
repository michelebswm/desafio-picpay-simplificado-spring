package com.michelew.desafiopycpay.services.exceptions;

public class UserDocumentInvalidException extends RuntimeException{

    public UserDocumentInvalidException(String message){
        super(message);
    }
}
