package com.starsgroup.techtest.feed.handler;

import com.starsgroup.techtest.domain.Header;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.parser.EventPacketParser;
import com.starsgroup.techtest.feed.parser.MarketPacketParser;
import com.starsgroup.techtest.feed.parser.OutcomePacketParser;
import com.starsgroup.techtest.feed.parser.ProviderFeedPacketParser;

import java.util.List;

public class ProviderFeedPacketHandlerFactory {

    private ProviderFeedPacketParser<Header> providerFeedPacketParser = new ProviderFeedPacketParser() {
        @Override
        public Header getEntity(Header header, List values) {
            return header;
        }
    };

    public ProviderFeedPacketHandler getHandler(ProviderFeedPacket packet) {
        Header header = providerFeedPacketParser.parse(packet);
        switch(header.getType()) {
            case EVENT: return new EventPacketHandler( new EventPacketParser());
            case MARKET: return new MarketPacketHandler( new MarketPacketParser());
            case OUTCOME: return new OutcomePacketHandler( new OutcomePacketParser());
            default:
                throw new IllegalArgumentException("unsupported data type found: " + header.getType());
        }
    }
}
