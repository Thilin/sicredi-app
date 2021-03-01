package com.example.sicrediapp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SessionClosedException extends RuntimeException {
    public SessionClosedException(String s) {
        super(s);
    }
}
