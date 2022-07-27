package com.example.orderproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class OrderProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProducerApplication.class, args);
	}



}
