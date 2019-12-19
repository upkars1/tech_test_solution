package com.starsgroup.techtest.domain;

public class EventBody extends Body {

    private final String eventId;
    private final String category;
    private final String subCategory;
    private final long startTime;

    private EventBody(String name, boolean displayed, boolean suspended, String eventId, String category, String subCategory, long startTime) {
        super(name, displayed, suspended);
        this.eventId = eventId;
        this.category = category;
        this.subCategory = subCategory;
        this.startTime = startTime;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public long getStartTime() {
        return startTime;
    }

    public static class Builder {

        private String name;
        private boolean displayed;
        private boolean suspended;
        private String eventId;
        private String category;
        private String subCategory;
        private long startTime;

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withDisplayed(boolean displayed){
            this.displayed = displayed;
            return this;
        }

        public Builder withSuspended(boolean suspended){
            this.suspended = suspended;
            return this;
        }

        public Builder withEventId( String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder withCategory( String category ) {
            this.category = category;
            return this;
        }

        public Builder withSubCategory(String subCategory) {
            this.subCategory = subCategory;
            return this;
        }

        public Builder withStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public EventBody build() {
            return new EventBody(name,displayed,suspended,eventId,category,subCategory,startTime);
        }
    }
}
