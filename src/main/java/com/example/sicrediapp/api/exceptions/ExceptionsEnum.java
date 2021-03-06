package com.example.sicrediapp.api.exceptions;

import lombok.*;

@Getter
public enum ExceptionsEnum {


    INVALID_DATA("/dados-invalidos", "Dados inválidos"),
    DUPLICATE_CPF("/cpf-duplicado", "O CPF já existe na base de dados"),
    RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
    SESSION_CLOSED("/sessao-fechada", "Não é possível votar em uma sessão fechada"),
    DUPLICATE_VOTE_SAME_SESSION("/voto-duplicado-mesma-sessao", "Um associado não pode votar mais de uma vez numa mesma sessão"),
    EXTERNAL_SERVICE_UNAVAILABLE("/servico-externo-offline", "Nada retornou do serviço. Verificar com o provedor"),
    UNABLE_TO_VOTE("/nao-pode-votar", "Associado não pode votar"),
    INVALID_SESSION_DURATION("/duracao-sessao-invalida", "A duração da sessão não pode ser menor que 1 minuto"),
    COUNT_VOTE_SESSION_OPEN("/votacao-sessao-aberta", "Não é possível ter o resultado da votação durante uma sessão aberta");


    private String uri;
    private String description;
    ExceptionsEnum(String path, String description){
        this.uri = "sicredi-app"+path;
        this.description = description;
    }
}
