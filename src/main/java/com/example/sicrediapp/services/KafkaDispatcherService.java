package com.example.sicrediapp.services;

public interface KafkaDispatcherService {

    public void send(String topic, String value, String value1);
}
