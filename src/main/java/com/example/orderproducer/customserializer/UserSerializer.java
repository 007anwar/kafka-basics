package com.example.orderproducer.customserializer;

import com.example.orderproducer.dto.UserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class UserSerializer implements Serializer<UserDetails> {
    @Override
    public byte[] serialize(String topic, UserDetails data) {
        byte[] response=null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
           response = objectMapper.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
