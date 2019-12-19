package com.starsgroup.techtest.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Market {

    private Header header;

    private MarketBody body;

    private Collection<Outcome> outcomes = new ArrayList<>();

    public Market(Header header, MarketBody body) {
        this (header,body,Collections.emptyList());
    }

    public Market(Header header, MarketBody body, Collection<Outcome> outcomes) {
        this.header = header;
        this.body = body;
        this.outcomes.addAll(outcomes);
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

    public Collection<Outcome> getOutcomes() {
        return Collections.unmodifiableCollection(outcomes);
    }

    public void setOutcomes(Outcome... outcomes) {
        this.outcomes.clear();
        addOutcomes(outcomes);
    }

    public void addOutcomes(Outcome... outcomes) {
        this.outcomes.addAll(Arrays.asList(outcomes));
    }
}
