package com.HosseiniAhmad.URLShorterner.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class PaymentException extends ResponseStatusException {
    public PaymentException(String message, ClientHttpResponse responseWithError, Object... args) throws IOException {
        super(
                HttpStatusCode.valueOf(500),
                String.format(message, args),
                new ResponseStatusException(
                        responseWithError.getStatusCode(),
                        new String(responseWithError.getBody().readAllBytes())
                )
        );
    }
}
