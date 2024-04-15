package com.HosseiniAhmad.URLShorterner.exception;

public class SubscriptionException extends RuntimeException {
    public SubscriptionException(String message, Object... args) {
        super(String.format(message, args));
    }

    public SubscriptionException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
