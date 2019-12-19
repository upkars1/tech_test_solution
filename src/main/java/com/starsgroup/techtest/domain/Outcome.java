package com.starsgroup.techtest.domain;

public class Outcome {

    private Header header;

    private OutcomeBody body;

    public Outcome(Header header, OutcomeBody body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public OutcomeBody getBody() {
        return body;
    }

    public void setBody(OutcomeBody body) {
        this.body = body;
    }
}
