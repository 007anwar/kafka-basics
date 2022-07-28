package com.example.orderproducer.service.impl;

import com.example.orderproducer.api.ProducerController;
import com.example.orderproducer.customserializer.UserSerializer;
import com.example.orderproducer.dto.UserDetails;
import com.example.orderproducer.service.Constants;
import com.example.orderproducer.service.IUserProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
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
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", UserSerializer.class.getName());
        //need to specify this to provide custom partitioning logic
        properties.setProperty("partitioner.class",CustomPartitioner.class.getName());
        KafkaProducer<Integer, UserDetails> producer = new KafkaProducer<>(properties);
        ProducerRecord<Integer, UserDetails> apimessages = new ProducerRecord<>(Constants.PARTITIONED, userDetails);
        log.info("Sending userDetails to kafka");
        Future<RecordMetadata> send = producer.send(apimessages);
        log.info("Message send to kafka topic: {}",send.get().topic());
    }
}
