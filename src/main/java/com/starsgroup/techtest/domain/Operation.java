package com.starsgroup.techtest.domain;

import java.util.Arrays;

public enum Operation {

    CREATE("create"),UPDATE("update");

    Operation( String name ) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public static Operation getOperation(String name ) {
        return Arrays.asList(Operation.values()).stream().filter( o -> o.getName().equals(name)).findAny().orElseThrow(() -> new IllegalArgumentException("unknown operation name " + name));
    }

}
