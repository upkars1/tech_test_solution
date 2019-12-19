package com.starsgroup.techtest.domain;

public abstract class Body {

    private final String name;
    private final boolean displayed;
    private final boolean suspended;

    public Body(String name, boolean displayed, boolean suspended) {
        this.name = name;
        this.displayed = displayed;
        this.suspended = suspended;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public boolean isSuspended() {
        return suspended;
    }
}
