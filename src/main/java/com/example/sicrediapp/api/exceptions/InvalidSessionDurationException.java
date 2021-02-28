package com.example.sicrediapp.api.exceptions;

public class InvalidScheduleDurationException extends RuntimeException {
    public InvalidScheduleDurationException(String s) {
        super(s);
    }
}
