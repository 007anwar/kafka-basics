package com.example.orderproducer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@RestController()
@RequestMapping("/consume")
public class ConsumerController {

    @GetMapping
    public List<String> getAllMessages()
    {
        List<String> allrecords=new ArrayList<>();
        Logger log = LoggerFactory.getLogger(ConsumerController.class);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("key.deserializer","org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.setProperty("group.id","OrderGroup");
        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("apimessages"));
        ConsumerRecords<Integer, String> poll = consumer.poll(Duration.ZERO);
         for(ConsumerRecord<Integer,String> record:poll)
         {
             log.info("Received Message: "+record.value());
             allrecords.add(record.value());
         }
         consumer.close();
         return allrecords;
    }
}
