package com.example.orderproducer.service;

import com.example.orderproducer.customserializer.UserDeSerializer;
import com.example.orderproducer.dto.UserDetails;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class ConsumerApplication implements Runnable {


    public ConsumerApplication() {
        Thread thread = new Thread(this::run);
        thread.start();
    }

    public void getAllMessages()
    {
        Logger log = LoggerFactory.getLogger(ConsumerApplication.class);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("key.deserializer", UserDeSerializer.class.getName());
        properties.setProperty("group.id","OrderGroup");
        KafkaConsumer<Integer, UserDetails> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(Constants.PARTITIONED));
        log.info("Starting to poll for new kafka message");
        try{
        while (true) {
            ConsumerRecords<Integer, UserDetails> poll = consumer.poll(Duration.ZERO);
            for (ConsumerRecord<Integer, UserDetails> record : poll) {
                log.info("Received Message: " + record.value());
            }
        }
    }
        finally {
            consumer.close();
        }
    }

    @Override
    public void run() {
        getAllMessages();
    }
}
