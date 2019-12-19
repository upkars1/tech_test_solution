package com.starsgroup.techtest.feed.parser;

import com.starsgroup.techtest.domain.Header;
import com.starsgroup.techtest.domain.Outcome;
import com.starsgroup.techtest.domain.OutcomeBody;

import java.util.List;

public class OutcomePacketParser extends ProviderFeedPacketParser<Outcome> {

    @Override
    public Outcome getEntity(Header header, List<String> values) {
        OutcomeBody body = new OutcomeBody.Builder()
                                .withMarketId(values.get(4))
                                .withOutcomeId(values.get(5))
                                .withName(values.get(6))
                                .withPrice(values.get(7))
                                .withDisplayed(getBoolean(values.get(8)))
                                .withSuspended(getBoolean(values.get(9)))
                                .build();
        return new Outcome( header , body );
    }
}
