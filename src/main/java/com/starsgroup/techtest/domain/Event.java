package com.starsgroup.techtest.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Event {

    private Header header;
    private EventBody body;
    private Collection<Market> markets;;

    public Event(Header header, EventBody body) {
        this ( header , body , Collections.emptyList() );
    }

    public Event(Header header, EventBody body, Collection<Market> markets) {
        this.header = header;
        this.body = body;
        this.markets = new ArrayList<>(markets);
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

    public Collection<Market> getMarkets() {
        return Collections.unmodifiableCollection(markets);
    }

    public void setMarkets(Market... markets) {
        this.markets.clear();
        addMarkets(markets);
    }

    public void addMarkets(Market... markets) {
        this.markets.addAll(Arrays.asList(markets));
    }
}
