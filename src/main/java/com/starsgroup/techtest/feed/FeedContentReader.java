package com.starsgroup.techtest.feed;

import java.io.IOException;
import java.util.Collection;

@FunctionalInterface
public interface FeedContentReader {

    FeedContentReaderContext connect() throws IOException;

    @FunctionalInterface
    interface Reader {
        Collection<String> read( int limit ) throws IOException;
    }

    @FunctionalInterface
    interface Closer {
        void close();
    }
}
