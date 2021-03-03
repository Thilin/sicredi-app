package com.example.sicrediapp.services.utils;

import lombok.*;

@Getter
public enum ExceptionsEnum {


    DUPLICATE_CPF(1L, "O CPF já existe na base de dados"),
    ASSOCIATE_NOT_FOUND(2L, "Associado não encontrado"),
    SESSION_NOT_FOUND(3L, "Sessão não encontrada"),
    SESSION_CLOSED(4L, "Não é possível votar em uma sessão fechada"),
    DUPLICATE_VOTE_SAME_SESSION(5L, "Um associado não pode votar mais de uma vez numa mesma sessão"),
    EXTERNAL_SERVICE_UNAVAILABLE(6L, "Nada retornou do serviço. Verificar com o provedor"),
    UNABLE_TO_VOTE(7l, "Associado não pode votar"),
    SCHEDULE_NOT_FOUND(8L, "Pauta não encontrada."),
    INVALID_SESSION_DURATION(9L, "A duração da sessão não pode ser menor que 1 minuto"),
    COUNT_VOTE_SESSION_OPEN(10L, "Não é possível ter o resultado da votação durante uma sessão aberta");


    private Long id;
    private String description;
    ExceptionsEnum(Long id, String description){
        this.id = id;
        this.description = description;
    }
}
