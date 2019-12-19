package com.starsgroup.techtest.feed.parser;

import com.starsgroup.techtest.domain.Event;
import com.starsgroup.techtest.domain.EventBody;
import com.starsgroup.techtest.domain.Header;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EventPacketParserTest {

    @InjectMocks
    private EventPacketParser testSubject;

    @Test
    public void shouldParsePacket() {

        String packet = "|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|";

        Event event = testSubject.parse(new ProviderFeedPacket(packet));

        Header header = event.getHeader();
        EventBody body = event.getBody();

        assertEquals( 2054 , header.getMsgId() );
        assertEquals( "create" , header.getOperation().getName() );
        assertEquals( "event" , header.getType().getName() );
        assertEquals( 1497359166352L , header.getTimestamp() );
        assertEquals( "ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2" , body.getEventId() );
        assertEquals( "Football" , body.getCategory() );
        assertEquals( "Sky Bet League Two" , body.getSubCategory() );
        assertEquals( "Accrington vs Cambridge" , body.getName() );
        assertEquals( 1497359216693L , body.getStartTime() );
        assertEquals( false , body.isDisplayed() );
        assertEquals( true , body.isSuspended() );
    }
}
