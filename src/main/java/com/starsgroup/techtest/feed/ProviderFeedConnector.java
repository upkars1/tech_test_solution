package com.starsgroup.techtest.feed;

import java.io.IOException;

public class ProviderFeedConnector {

    private final FeedContentReader reader;

    public ProviderFeedConnector( final FeedContentReader reader ) {
        this.reader = reader;
    }

    public FeedContentReaderContext getFeed() throws IOException {
        return reader.connect();
    }
}