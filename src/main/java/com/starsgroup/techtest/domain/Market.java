package com.starsgroup.techtest.domain;

import java.util.*;

public class Market {

    private Header header;

    private MarketBody body;

    private LinkedList<Outcome> outcomes = new LinkedList<>();

    public Market(Header header, MarketBody body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public MarketBody getBody() {
        return body;
    }

    public void setBody(MarketBody body) {
        this.body = body;
    }

    public void addOutcome(Outcome outcome) {
        outcomes.addLast(outcome);
    }

    public boolean isInProgress( Outcome outcome ) {
        return outcomes.getLast() == outcome;
    }
}
