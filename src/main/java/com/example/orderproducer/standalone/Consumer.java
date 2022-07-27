package com.example.orderproducer.standalone;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {
    public static void main(String[] args) {
        while (true)
        kafkaConsumer();
    }
    public static void kafkaConsumer()
    {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.setProperty("group.id","OrderGroup");
        KafkaConsumer<String, Integer> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("OrderTopic"));
        while (true){

            System.out.println("READING RECORD ");
            ConsumerRecords<String, Integer> poll = consumer.poll(Duration.ofSeconds(30));
            for (ConsumerRecord<String, Integer> consumerRecords:poll) {
                System.out.println("Key"+consumerRecords.key());
                System.out.println("Value"+consumerRecords.value());
            }
        }
}}
