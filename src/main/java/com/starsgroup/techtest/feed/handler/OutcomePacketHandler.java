package com.starsgroup.techtest.feed.handler;

import com.starsgroup.techtest.domain.Outcome;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;
import com.starsgroup.techtest.feed.parser.OutcomePacketParser;

import java.io.IOException;

public class OutcomePacketHandler implements ProviderFeedPacketHandler {

    private OutcomePacketParser outcomePacketParser;

    public OutcomePacketHandler(OutcomePacketParser outcomePacketParser) {
        this.outcomePacketParser = outcomePacketParser;
    }

    @Override
    public void handle(EventFeedConsumer eventFeedConsumer, ProviderFeedPacket providerFeedPacket) throws IOException {
        Outcome outcome = outcomePacketParser.parse(providerFeedPacket);
        eventFeedConsumer.updateInProgress(outcome);
    }
}
