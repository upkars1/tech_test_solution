package com.starsgroup.techtest.feed.parser;

import com.starsgroup.techtest.domain.Header;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProviderFeedPacketParserTest {

    private static final String packetHeader = "|2451|create|event|1";

    private ProviderFeedPacketParser<List<String>> testSubject = new ProviderFeedPacketParser<List<String>>() {
        @Override
        public List<String> getEntity(Header header, List<String> values) {
            return values;
        }
    };

    @Test
    public void shouldParseSimpleSingleValue() {
        String body = "|value|";
        List<String> values = testSubject.parse(new ProviderFeedPacket(packetHeader + body));
        assertEquals( 5, values.size() );
        assertEquals( "value" , values.get(4) );
    }

    @Test
    public void shouldParseSimpleMultipleValues() {
        String body = "|value1|value2|value3|";
        List<String> values = testSubject.parse(new ProviderFeedPacket(packetHeader + body));
        assertEquals( 7, values.size() );
        assertEquals( "value1" , values.get(4) );
        assertEquals( "value2" , values.get(5) );
        assertEquals( "value3" , values.get(6) );
    }

    @Test
    public void shouldParseValueContainingEscapeSequence() {
        String body = "|val\\|ue|";
        List<String> values = testSubject.parse(new ProviderFeedPacket(packetHeader + body));
        assertEquals( 5, values.size() );
        assertEquals( "value" , values.get(4) );
    }

    @Test
    public void shouldParseValueContainingMultipleEscapeSequences() {
        String body = "|val\\|ue\\||";
        List<String> values = testSubject.parse(new ProviderFeedPacket(packetHeader + body));
        assertEquals( 5, values.size() );
        assertEquals( "value" , values.get(4) );
    }

    @Test
    public void shouldParseMultipleValuesContainingEscapeSequences() {
        String body = "|val\\|ue1|value2|\\|valu\\|e3|";
        List<String> values = testSubject.parse(new ProviderFeedPacket(packetHeader + body));
        assertEquals( 7, values.size() );
        assertEquals( "value1" , values.get(4) );
        assertEquals( "value2" , values.get(5) );
        assertEquals( "value3" , values.get(6) );
    }
}
