package com.example.orderproducer.standalone;

import com.example.orderproducer.service.OrderCallback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {
    public static void main(String[] args) {
        kafkaProducer();
    }

	private static void kafkaProducer()
	{
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers","localhost:9092");
		properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		properties.setProperty("value.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
		KafkaProducer<String, Integer> producer = new KafkaProducer<>(properties);

		ProducerRecord<String, Integer> record = new ProducerRecord<>("OrderTopic", "Mac book", 10);
		try{
			while (true) {
				System.out.println("SENDING NEW RECORD TO KAFKA");
				producer.send(record, new OrderCallback());
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			producer.close();
		}
	}
}
