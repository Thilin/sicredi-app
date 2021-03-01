package com.example.sicrediapp.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToVoteException extends RuntimeException {
    public UnableToVoteException(String associado_não_pode_votar) {
        super(associado_não_pode_votar);
    }
}
