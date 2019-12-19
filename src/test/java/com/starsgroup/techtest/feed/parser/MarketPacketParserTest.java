package com.starsgroup.techtest.feed.parser;


import com.starsgroup.techtest.domain.Header;
import com.starsgroup.techtest.domain.Market;
import com.starsgroup.techtest.domain.MarketBody;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MarketPacketParserTest {

    @InjectMocks
    private MarketPacketParser testSubject;

    @Test
    public void shouldParsePacket() {

        String packet = "|2054|create|market|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|market-id-1|market-name-1|0|1|";

        Market market = testSubject.parse(new ProviderFeedPacket(packet));

        Header header = market.getHeader();
        MarketBody body = market.getBody();

        assertEquals( 2054 , header.getMsgId() );
        assertEquals( "create" , header.getOperation().getName() );
        assertEquals( "market" , header.getType().getName() );
        assertEquals( 1497359166352L , header.getTimestamp() );
        assertEquals( "ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2" , body.getEventId() );
        assertEquals( "market-id-1" , body.getMarketId() );
        assertEquals( "market-name-1" , body.getName() );
        assertEquals( false , body.isDisplayed() );
        assertEquals( true , body.isSuspended() );

    }
}
