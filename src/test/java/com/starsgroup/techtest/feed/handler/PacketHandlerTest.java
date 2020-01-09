package com.starsgroup.techtest.feed.handler;

import com.starsgroup.techtest.domain.*;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.consumer.EventFeedConsumer;
import com.starsgroup.techtest.feed.parser.EventPacketParser;
import com.starsgroup.techtest.feed.parser.MarketPacketParser;
import com.starsgroup.techtest.feed.parser.OutcomePacketParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PacketHandlerTest {

    @Mock
    private EventFeedConsumer eventFeedConsumer;

    @Mock
    private ProviderFeedPacket packet;


    @Test
    public void shouldHandleEventPacket() throws IOException {
        Event event = mock(Event.class);
        EventPacketParser eventPacketParser = mock(EventPacketParser.class);
        EventPacketHandler testSubject = new EventPacketHandler( eventPacketParser );

        when( eventPacketParser.parse(packet) ).thenReturn(event);

        testSubject.handle( eventFeedConsumer , packet );

        verify( eventFeedConsumer).updateInProgress(event);
    }

    @Test
    public void shouldHandleMarketPacket() throws IOException {
        Market market = mock(Market.class);
        MarketPacketParser marketPacketParser = mock(MarketPacketParser.class);

        MarketPacketHandler testSubject = new MarketPacketHandler(marketPacketParser);

        when (marketPacketParser.parse(packet)).thenReturn( market );

        testSubject.handle( eventFeedConsumer, packet);

        verify( eventFeedConsumer ).updateInProgress(market);
    }

    @Test
    public void shouldHandleOutcomePacket() throws IOException {
        Outcome outcome = mock(Outcome.class);
        OutcomePacketParser outcomePacketParser = mock(OutcomePacketParser.class);

        OutcomePacketHandler testSubject = new OutcomePacketHandler(outcomePacketParser);

        when (outcomePacketParser.parse(packet)).thenReturn( outcome );

        testSubject.handle( eventFeedConsumer, packet);

        verify( eventFeedConsumer ).updateInProgress(outcome);
    }
}
