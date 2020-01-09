package com.starsgroup.techtest.feed.consumer;

import com.starsgroup.techtest.domain.*;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.handler.ProviderFeedPacketHandler;
import com.starsgroup.techtest.feed.handler.ProviderFeedPacketHandlerFactory;
import com.starsgroup.techtest.persistence.EventDAO;
import com.starsgroup.techtest.persistence.MarketDAO;
import com.starsgroup.techtest.persistence.OutcomeDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static com.starsgroup.techtest.domain.Operation.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventFeedConsumerTest {

    private EventFeedConsumer testSubject;

    @Mock
    private ProviderFeedPacketHandlerFactory packetHandlerFactory;

    @Mock
    private EventDAO eventDAO;

    @Mock
    private MarketDAO marketDAO;

    @Mock
    private OutcomeDAO outcomeDAO;

    @Mock
    private ProviderFeedPacketHandler eventPacketHandler;

    @Captor
    private ArgumentCaptor<ProviderFeedPacket> packetCaptor;

    @Before
    public void setUp() {
        testSubject = new EventFeedConsumer( eventDAO, marketDAO, outcomeDAO, packetHandlerFactory );
    }

    @Test
    public void shouldHandlePacket() throws IOException {

        String eventData = "|my|event|create|data1|";

        when(packetHandlerFactory.getHandler(any(ProviderFeedPacket.class))).thenReturn( eventPacketHandler );

        ConsumerRecord<String, String> record = new ConsumerRecord<>( "topic" , 0, 0, "",  eventData  );

        testSubject.consume(record.value());

        verify( eventPacketHandler ).handle( eq(testSubject), packetCaptor.capture());

        assertEquals( eventData , packetCaptor.getValue().getContent() );
    }

    @Test
    public void shouldUpdateEventWhenFirstEventInProgress() throws IOException {
        Event event = getEventWithOperationType(CREATE);

        testSubject.updateInProgress( event );

        assertEquals( true , testSubject.inProgressIs( event ));
    }

    @Test
    public void shouldSaveCurrentEventInProgressAndReplaceWithNew() throws IOException {
        Event current = getEventWithOperationType(CREATE);
        Event newEvent = getEventWithOperationType(CREATE);

        testSubject.updateInProgress( current );
        testSubject.updateInProgress( newEvent );

        verify( eventDAO ).save( current );
        assertEquals( true, testSubject.inProgressIs( newEvent ));
    }

    @Test
    public void shouldUpdateMarket() throws IOException {

        Event event = getEventWithOperationType(CREATE);

        testSubject.updateInProgress( event );

        Market market = getMarketWithOperationType(CREATE);

        testSubject.updateInProgress( market );

        assertEquals( true , testSubject.inProgressIs( market ));
    }

    @Test
    public void shouldUpdateOutcome() throws IOException {
        Market market = getMarketWithOperationType( CREATE );
        Outcome outcome = getOutcomeWithOperationType(CREATE);
        when(market.isInProgress(outcome)).thenReturn(true);

        Event event = getEventWithOperationType(CREATE);

        testSubject.updateInProgress(event);
        testSubject.updateInProgress(market);
        testSubject.updateInProgress(outcome);

        assertEquals ( true , testSubject.inProgressIs(outcome) );
        assertEquals( true , market.isInProgress( outcome ));
    }

    private Market getMarketWithOperationType(Operation operation) {
        Market market =  mock( Market.class );
        Header marketHeader = mock( Header.class );
        when( market.getHeader() ).thenReturn(marketHeader);
        when( marketHeader.getOperation()).thenReturn(operation);
        return market;
    }

    private Event getEventWithOperationType(Operation operation) {
        Event event =  mock( Event.class );
        Header header = mock( Header.class );
        when( event.getHeader() ).thenReturn(header);
        when( header.getOperation()).thenReturn(operation);
        return event;
    }

    private Outcome getOutcomeWithOperationType( Operation operation ) {
        Outcome outcome =  mock( Outcome.class );
        Header outcomeHeader = mock( Header.class );
        when( outcome.getHeader() ).thenReturn(outcomeHeader);
        when( outcomeHeader.getOperation()).thenReturn(operation);
        return outcome;
    }
}
