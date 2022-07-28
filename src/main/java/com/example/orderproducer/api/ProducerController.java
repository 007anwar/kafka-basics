package com.example.orderproducer.api;

import com.example.orderproducer.dto.UserDetails;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/produce")
public class ProducerController {

    @PostMapping
    public void produce(@RequestBody UserDetails userDetails) throws ExecutionException, InterruptedException {
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
