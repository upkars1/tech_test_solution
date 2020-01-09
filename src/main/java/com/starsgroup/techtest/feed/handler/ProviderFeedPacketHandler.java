package com.starsgroup.techtest.feed.handler;

import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;

import java.io.IOException;

@FunctionalInterface
public interface ProviderFeedPacketHandler {

    void handle(EventFeedConsumer eventFeedConsumer, ProviderFeedPacket providerFeedPacket) throws IOException;
}
