package com.example.sicrediapp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ExternalServiceUnavailableException extends RuntimeException {
    public ExternalServiceUnavailableException(String s) {
        super(s);
    }
}
