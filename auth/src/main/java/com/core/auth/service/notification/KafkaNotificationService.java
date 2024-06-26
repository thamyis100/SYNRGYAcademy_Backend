package com.core.auth.service.notification;

import com.core.auth.service.IKafkaNotificationService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificationService implements IKafkaNotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "promo_notifications1"; // Kafka topic name

    @Autowired
    public KafkaNotificationService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendNotification(String message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Sent message to Kafka: " + message);
    }
}
