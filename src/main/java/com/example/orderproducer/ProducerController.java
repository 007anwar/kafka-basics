package com.example.orderproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController()
@RequestMapping("/produce")
public class ProducerController {

    @PostMapping
    public void produce(@RequestBody Message message) throws ExecutionException, InterruptedException {
        Logger log = LoggerFactory.getLogger(ProducerController.class);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<Integer, String> apimessages = new ProducerRecord<>("apimessages", message.getMessage());
        log.info("Sending message to kafka");
        Future<RecordMetadata> send = producer.send(apimessages);
        log.info("Message send to kafka topic: {}",send.get().topic());
    }
}
