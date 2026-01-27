package com.product.automatization.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.product.automatization.DTO.AuthRequest;
import com.product.automatization.DTO.AuthResponse;
import com.product.automatization.DTO.EmailRequest;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AuthConsumerService {
    private static final Logger log = LoggerFactory.getLogger(AuthConsumerService.class);
    private final ObjectMapper objectMapper;
    private final AuthProducerService producer;
    private static final String RESPONSE_TOPIC = "auth-responses";
    private EmailService emailService;

    @Autowired
    public AuthConsumerService(ObjectMapper objectMapper, AuthProducerService producer, EmailService emailService) {
        this.objectMapper = objectMapper;
        this.producer = producer;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "spring.auth.request", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String payload) {
        try {
            log.info("Payload recibido: {}", payload);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(payload);
            JsonNode data = root.get("AuthRequest");

            AuthRequest request = mapper.treeToValue(data, AuthRequest.class);
            log.info("Objeto AuthRequest mapeado: {} :");
            log.info(request.getEmail());
            log.info(request.getUsername());
            emailService.sendEmail(new EmailRequest(request.getEmail(), "BIENVENIDA", null));

        } catch (Exception e) {
            log.error("Error procesando mensaje Kafka", e);
        }
    }
}
