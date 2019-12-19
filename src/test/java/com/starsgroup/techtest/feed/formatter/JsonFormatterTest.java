package com.starsgroup.techtest.feed.formatter;

import com.starsgroup.techtest.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JsonFormatterTest {

    @InjectMocks
    private JsonFormatter testSubject;

    @Test
    public void shouldFormat() {

        Header header = new Header( 1L , Operation.CREATE, PacketType.EVENT, 2L );
        EventBody body = new EventBody.Builder().withCategory( "Football" ).withDisplayed(true).withEventId("event-id-1")
                .withName("event-name-1").withStartTime(3L).withSubCategory("Champions League").withSuspended(false).build();
        Event event = new Event(header , body);
        Header marketHeader = new Header ( 2L, Operation.CREATE, PacketType.MARKET, 3L );
        MarketBody market1Body = new MarketBody.Builder().withEventId("event-id-1").withMarketId("market-id-1").withDisplayed(true).withSuspended(false).withName("market-name-1").build();
        MarketBody market2Body = new MarketBody.Builder().withEventId("event-id-1").withMarketId("market-id-2").withDisplayed(true).withSuspended(false).withName("market-name-2").build();

        event.addMarkets();

        String json = testSubject.toJson(event);

        System.out.println(json);
    }
}
