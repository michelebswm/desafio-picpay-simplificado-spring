package com.michelew.desafiopycpay.domain.enums;

import com.michelew.desafiopycpay.services.exceptions.UserDocumentInvalidException;

public enum TypeUser {
    COMMON(1),
    RETAILER(2);

    private int code;

    TypeUser(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TypeUser valueOf(int code){
        for (TypeUser value : TypeUser.values()){
            if (value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid TypeUser code.");
    }
}
