package com.michelew.desafiopycpay.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Object id){
        super("Resource not found. Id: " + id);
    }
}
