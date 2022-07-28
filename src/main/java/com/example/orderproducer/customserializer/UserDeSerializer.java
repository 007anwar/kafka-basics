package com.example.orderproducer.customserializer;

import com.example.orderproducer.dto.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class UserDeSerializer implements Deserializer<UserDetails> {
    @Override
    public UserDetails deserialize(String topic, byte[] data) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetails userDetails=null;
        try {
            userDetails = objectMapper.readValue(data, UserDetails.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userDetails;
    }
}
