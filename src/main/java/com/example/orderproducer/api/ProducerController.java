package com.example.orderproducer.api;

import com.example.orderproducer.customserializer.UserSerializer;
import com.example.orderproducer.dto.UserDetails;
import com.example.orderproducer.service.Constants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
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
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", UserSerializer.class.getName());
        KafkaProducer<Integer, UserDetails> producer = new KafkaProducer<>(properties);
        ProducerRecord<Integer, UserDetails> apimessages = new ProducerRecord<>(Constants.PARTITIONED, userDetails);
        log.info("Sending userDetails to kafka");
        Future<RecordMetadata> send = producer.send(apimessages);
        log.info("Message send to kafka topic: {}",send.get().topic());
    }
}
