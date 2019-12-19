package com.starsgroup.techtest.domain;

public class OutcomeBody extends Body {

    private final String marketId;
    private final String outcomeId;
    private final String price;

    private OutcomeBody(String name, boolean displayed, boolean suspended, String marketId, String outcomeId, String price) {
        super(name, displayed, suspended);
        this.marketId = marketId;
        this.outcomeId = outcomeId;
        this.price = price;
    }

    public String getMarketId() {
        return marketId;
    }

    public String getOutcomeId() {
        return outcomeId;
    }

    public String getPrice() {
        return price;
    }

    public static class Builder {

        private String name;
        private boolean displayed;
        private boolean suspended;
        private String marketId;
        private String outcomeId;
        private String price;

        public Builder withName( String name ) {
            this.name = name;
            return this;
        }

        public Builder withDisplayed(boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public Builder withSuspended(boolean suspended) {
            this.suspended = suspended;
            return this;
        }

        public Builder withMarketId( String marketId ) {
            this.marketId = marketId;
            return this;
        }

        public Builder withOutcomeId( String outcomeId ) {
            this.outcomeId = outcomeId;
            return this;
        }

        public Builder withPrice( String price ) {
            this.price = price;
            return this;
        }

        public OutcomeBody build() {
            return new OutcomeBody(name,displayed,suspended,marketId,outcomeId,price);
        }
    }

}
