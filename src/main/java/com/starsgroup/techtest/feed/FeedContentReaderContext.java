package com.starsgroup.techtest.feed;

import java.io.IOException;
import java.util.Collection;

public class FeedContentReaderContext {

    private final FeedContentReader.Reader reader;
    private final FeedContentReader.Closer closer;

    public FeedContentReaderContext(FeedContentReader.Reader reader, FeedContentReader.Closer closer) {
        this.reader = reader;
        this.closer = closer;
    }

    public Collection<String> read(int limit ) throws IOException {
        return reader.read(limit);
    }

    public void close() {
        closer.close();
    }
}
