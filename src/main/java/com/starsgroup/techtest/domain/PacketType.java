package com.starsgroup.techtest.domain;

import java.util.Arrays;

public enum PacketType {

    EVENT("event"), MARKET("market"), OUTCOME("outcome");

    PacketType( String name ) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public static PacketType getPacketType( String name ) {
        return Arrays.asList(PacketType.values()).stream().filter(type -> type.getName().equals(name)).findAny().orElseThrow(() -> new IllegalArgumentException("unknown packet type: " + name));
    }
}
