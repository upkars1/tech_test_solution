package com.starsgroup.techtest.feed.consumer;

import com.starsgroup.techtest.domain.Event;
import com.starsgroup.techtest.domain.Market;
import com.starsgroup.techtest.domain.Operation;
import com.starsgroup.techtest.domain.Outcome;
import com.starsgroup.techtest.feed.ProviderFeedPacket;
import com.starsgroup.techtest.feed.handler.ProviderFeedPacketHandler;
import com.starsgroup.techtest.feed.handler.ProviderFeedPacketHandlerFactory;
import com.starsgroup.techtest.persistence.EventDAO;
import com.starsgroup.techtest.persistence.MarketDAO;
import com.starsgroup.techtest.persistence.OutcomeDAO;

import java.io.IOException;
import java.util.LinkedList;

public class EventFeedConsumer {

    private EventDAO eventDAO;

    private MarketDAO marketDAO;

    private OutcomeDAO outcomeDAO;

    private Event inProgressEvent;

    private LinkedList<Market> inProgressMarkets = new LinkedList<>();

    private ProviderFeedPacketHandlerFactory packetHandlerFactory;

    public EventFeedConsumer(EventDAO eventDAO, MarketDAO marketDAO, OutcomeDAO outcomeDAO, ProviderFeedPacketHandlerFactory packetHandlerFactory) {
        this.eventDAO = eventDAO;
        this.marketDAO = marketDAO;
        this.outcomeDAO = outcomeDAO;
        this.packetHandlerFactory = packetHandlerFactory;
    }

    public void consume(String packet) {
        ProviderFeedPacket providerFeedPacket = new ProviderFeedPacket(packet);
        ProviderFeedPacketHandler handler = packetHandlerFactory.getHandler(providerFeedPacket);
        try {
            handler.handle( this, providerFeedPacket );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean inProgressIs( Event candidate ) {
        return inProgressEvent == candidate;
    }


    public void updateInProgress(Event event) throws IOException {
        if ( event.getHeader().getOperation().equals(Operation.UPDATE) ) {
            eventDAO.save(event);
        } else if ( inProgressEvent == null ) {
            inProgressEvent = event;
        } else  {
            Event completed = inProgressEvent;
            completed.setMarkets(inProgressMarkets);
            eventDAO.save(completed);
            inProgressEvent = event;
            inProgressMarkets = new LinkedList<>();
        }
    }

    /**
     * Conditional code ensures that there is an inProgress Event that the Market will be a part of.
     * In a production system there would need to be greater intelligence to perhaps retrieve and cache into progress a partially complete event.
     */
    public void updateInProgress(Market market) throws IOException {
        if ( market.getHeader().getOperation().equals(Operation.UPDATE) ) {
            marketDAO.save(market);
        } else if (inProgressEvent != null) {
            inProgressMarkets.addLast(market);
        }
    }

    /**
     *
     * Conditional code ensures that there is an inProgress Market that the Outcome will be a part of.
     * In a production system there would need to be greater intelligence to perhaps retrieve and cache into progress a partially complete event.
     */
    public void updateInProgress(Outcome outcome) throws IOException {
        if ( outcome.getHeader().getOperation().equals(Operation.UPDATE)) {
            outcomeDAO.save(outcome);
        } else if (!inProgressMarkets.isEmpty()) {
            inProgressMarkets.getLast().addOutcome(outcome);
        }
    }

    public boolean inProgressIs(Market candidate) {
        return inProgressMarkets.getLast() == candidate;
    }

    public boolean inProgressIs(Outcome outcome) {
        return inProgressMarkets.getLast().isInProgress(outcome);
    }

}
