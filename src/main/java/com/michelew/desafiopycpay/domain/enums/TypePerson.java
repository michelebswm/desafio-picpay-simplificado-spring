package com.michelew.desafiopycpay.domain.enums;

public enum TypePerson {
    JURIDICA(1),
    FISICA(2);

    private int code;

    private TypePerson(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TypePerson valueOf(int code){
        for (TypePerson value : TypePerson.values()){
            if (value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid TypePerson code.");
    }
}
