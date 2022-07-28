package com.example.orderproducer.service.impl;

import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderCallback implements org.apache.kafka.clients.producer.Callback {
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        System.out.println("ORDER CALLBACK");
        System.out.println(String.valueOf(metadata.partition()));
        System.out.println(String.valueOf(metadata.offset()));
    }
}
