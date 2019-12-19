package com.starsgroup.techtest.feed;

public class ProviderFeedConfiguration {

    private final String ip;
    private final int port;

    public ProviderFeedConfiguration(final String ip, final int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
