package com.core.auth.config;

import com.core.auth.service.notification.FirebaseMessagingService;
import com.core.auth.service.notification.KafkaNotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class PromoNotificationScheduler {

    private final KafkaNotificationService kafkaNotificationService;
    private final FirebaseMessagingService firebaseMessagingService;

    @Autowired
    public PromoNotificationScheduler(KafkaNotificationService kafkaNotificationService,
                                      FirebaseMessagingService firebaseMessagingService) {
        this.kafkaNotificationService = kafkaNotificationService;
        this.firebaseMessagingService = firebaseMessagingService;
    }

    // Scheduled to run every day at 12:00 PM
    @Scheduled(cron = "0 0 12 * * ?", zone = "Asia/Jakarta")
    public void sendPromoNotification() {
        String promoMessage = "Check out our latest promotions!";
        // Send notification to Kafka
        kafkaNotificationService.sendNotification(promoMessage);

        // Send notification to Firebase
        firebaseMessagingService.sendFirebaseNotificationToAll(promoMessage);
    }
}
