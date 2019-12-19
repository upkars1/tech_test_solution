package com.starsgroup.techtest.feed.parser;

import com.starsgroup.techtest.domain.*;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OutcomePacketParserTest {

    @InjectMocks
    private OutcomePacketParser testSubject;

    @Test
    public void shouldParsePacket() {

        String packet = "|2054|create|outcome|1497359166352|market-id-1|outcome-id-1|outcome-name-1|3.99|0|1|";

        Outcome outcome = testSubject.parse(new ProviderFeedPacket(packet));

        Header header = outcome.getHeader();
        OutcomeBody body = outcome.getBody();

        assertEquals( 2054 , header.getMsgId() );
        assertEquals( "create" , header.getOperation().getName() );
        assertEquals( "outcome" , header.getType().getName() );
        assertEquals( 1497359166352L , header.getTimestamp() );
        assertEquals( "market-id-1" , body.getMarketId() );
        assertEquals( "outcome-id-1" , body.getOutcomeId() );
        assertEquals( "outcome-name-1" , body.getName() );
        assertEquals( "3.99" , body.getPrice());
        assertEquals( false , body.isDisplayed() );
        assertEquals( true , body.isSuspended() );
    }
}
