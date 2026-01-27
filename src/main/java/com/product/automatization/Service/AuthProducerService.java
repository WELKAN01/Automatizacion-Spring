package com.product.automatization.Service;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.product.automatization.DTO.AuthResponse;

@Service
public class AuthProducerService {
        private static final Logger log = LoggerFactory.getLogger(AuthProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public AuthProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(AuthResponse response) throws ExecutionException, InterruptedException {
        //kafkaTemplate.send("auth-responses", serializable(response)).get();
    
    }
}
