package com.starsgroup.techtest.feed.parser;

import com.starsgroup.techtest.domain.*;
import java.util.List;

public class EventPacketParser extends ProviderFeedPacketParser<Event> {

    @Override
    public Event getEntity(Header header, List<String> values) {
        EventBody body = new EventBody.Builder()
                .withEventId( values.get(4) )
                .withCategory( values.get(5) )
                .withSubCategory( values.get(6))
                .withName(values.get(7))
                .withStartTime(Long.parseLong(values.get(8)))
                .withDisplayed(getBoolean(values.get(9)))
                .withSuspended(getBoolean(values.get(10)))
                .build();
        return new Event( header , body );
    }
}
