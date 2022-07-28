package com.example.orderproducer.service.impl;

import com.example.orderproducer.api.ProducerController;
import com.example.orderproducer.dto.UserDetails;
import com.example.orderproducer.service.IUserProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class UserProducerImpl implements IUserProducer {
    @Override
    public void produce(UserDetails userDetails) throws ExecutionException, InterruptedException {
        Logger log = LoggerFactory.getLogger(ProducerController.class);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","com.example.orderproducer.customserializer.UserSerializer");
        KafkaProducer<Integer, UserDetails> producer = new KafkaProducer<>(properties);
        ProducerRecord<Integer, UserDetails> apimessages = new ProducerRecord<>("userdetails", userDetails);
        log.info("Sending userDetails to kafka");
        Future<RecordMetadata> send = producer.send(apimessages);
        log.info("Message send to kafka topic: {}",send.get().topic());
    }
}
