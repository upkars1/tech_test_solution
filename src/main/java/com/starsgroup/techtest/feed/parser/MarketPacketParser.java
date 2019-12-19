package com.starsgroup.techtest.feed.parser;

import com.starsgroup.techtest.domain.Header;
import com.starsgroup.techtest.domain.Market;
import com.starsgroup.techtest.domain.MarketBody;

import java.util.List;

public class MarketPacketParser extends ProviderFeedPacketParser<Market> {

    @Override
    public Market getEntity(Header header, List<String> values) {
        MarketBody body = new MarketBody.Builder()
                                .withEventId(values.get(4))
                                .withMarketId(values.get(5))
                                .withName(values.get(6))
                                .withDisplayed(getBoolean(values.get(7)))
                                .withSuspended(getBoolean(values.get(8)))
                                .build();
        return new Market( header, body );
    }
}
