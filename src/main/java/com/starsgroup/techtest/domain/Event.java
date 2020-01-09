package com.starsgroup.techtest.domain;

import java.util.*;

public class Event {

    private Header header;
    private EventBody body;
    private Collection<Market> markets = new LinkedList<>();

    public Event(Header header, EventBody body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public EventBody getBody() {
        return body;
    }

    public void setBody(EventBody body) {
        this.body = body;
    }

    public void setMarkets(Collection<Market> markets) {
        this.markets = markets;
    }
}
