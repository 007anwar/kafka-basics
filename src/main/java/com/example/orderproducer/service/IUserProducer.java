package com.example.orderproducer.service;

import com.example.orderproducer.dto.UserDetails;

import java.util.concurrent.ExecutionException;

public interface IUserProducer {
void produce(UserDetails userDetails) throws ExecutionException, InterruptedException;
}
