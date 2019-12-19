package com.starsgroup.techtest.feed;

public class FeedContentReaderException extends RuntimeException {

    public FeedContentReaderException(String message) {
        super(message);
    }

    public FeedContentReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedContentReaderException(Throwable cause) {
        super(cause);
    }

    public FeedContentReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
