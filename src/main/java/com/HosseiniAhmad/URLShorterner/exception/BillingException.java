package com.HosseiniAhmad.URLShorterner.exception;

public class BillingException extends RuntimeException {
    public BillingException(String message, Object... args) {
        super(String.format(message, args));
    }

    public BillingException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
