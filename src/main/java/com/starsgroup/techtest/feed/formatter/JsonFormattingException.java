package com.starsgroup.techtest.feed.formatter;

public class JsonFormattingException extends RuntimeException {

    public JsonFormattingException(String message) {
        super(message);
    }

    public JsonFormattingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonFormattingException(Throwable cause) {
        super(cause);
    }

    public JsonFormattingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
