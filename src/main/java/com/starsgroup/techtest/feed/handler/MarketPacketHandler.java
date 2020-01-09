package com.starsgroup.techtest.feed.handler;

import com.starsgroup.techtest.domain.Market;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;
import com.starsgroup.techtest.feed.parser.MarketPacketParser;

import java.io.IOException;

public class MarketPacketHandler implements ProviderFeedPacketHandler {

    private MarketPacketParser marketPacketParser;

    public MarketPacketHandler(MarketPacketParser marketPacketParser) {
        this.marketPacketParser = marketPacketParser;
    }

    @Override
    public void handle(EventFeedConsumer eventFeedConsumer, ProviderFeedPacket providerFeedPacket) throws IOException {
        Market parse = marketPacketParser.parse(providerFeedPacket);
        eventFeedConsumer.updateInProgress( parse );
    }
}
