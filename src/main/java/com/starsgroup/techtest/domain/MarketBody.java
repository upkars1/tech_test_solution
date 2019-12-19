package com.starsgroup.techtest.domain;

public class MarketBody extends Body {

    private final String marketId;
    private final String eventId;

    private MarketBody(String name, boolean displayed, boolean suspended, String eventId, String marketId) {
        super(name, displayed, suspended);
        this.eventId = eventId;
        this.marketId = marketId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getMarketId() {
        return marketId;
    }

    public static class Builder {

        private String name;
        private boolean displayed;
        private boolean suspended;
        private String eventId;
        private String marketId;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDisplayed (boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public Builder withSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public Builder withEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withMarketId(String marketId) {
            this.marketId = marketId;
            return this;
        }

        public MarketBody build() {
            return new MarketBody(name, displayed, suspended, eventId, marketId );
        }
    }

}
