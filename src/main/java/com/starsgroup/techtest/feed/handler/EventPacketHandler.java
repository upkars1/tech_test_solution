package com.starsgroup.techtest.feed.handler;

import com.starsgroup.techtest.domain.Event;
import com.starsgroup.techtest.domain.Operation;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;
import com.starsgroup.techtest.feed.parser.EventPacketParser;

import java.io.IOException;

public class EventPacketHandler implements ProviderFeedPacketHandler {

    private EventPacketParser eventPacketParser;

    public EventPacketHandler(EventPacketParser eventPacketParser) {
        this.eventPacketParser = eventPacketParser;
    }

    @Override
    public void handle(EventFeedConsumer eventFeedConsumer, ProviderFeedPacket providerFeedPacket) throws IOException {
        Event newEvent = eventPacketParser.parse(providerFeedPacket);
        eventFeedConsumer.updateInProgress(newEvent);
    }
}
