package com.ecommerce.enums;

public enum RoleName {

    SELLER(0),BUYER(1);

    private final int value;

    RoleName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
