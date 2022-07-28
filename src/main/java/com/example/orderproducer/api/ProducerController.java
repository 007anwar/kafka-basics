package com.example.orderproducer.api;

import com.example.orderproducer.dto.UserDetails;
import com.example.orderproducer.service.IUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RestController()
@RequestMapping("/produce")
public class ProducerController {

    private final IUserProducer producer;
    @Autowired
    public ProducerController(IUserProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public void produce(@RequestBody UserDetails userDetails) throws ExecutionException, InterruptedException {
          producer.produce(userDetails);
    }
}
