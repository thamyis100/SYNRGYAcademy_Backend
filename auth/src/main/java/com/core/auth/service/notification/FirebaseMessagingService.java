package com.core.auth.service.notification;

import com.core.auth.service.IFirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FirebaseMessagingService implements IFirebaseMessagingService {

    public void sendFirebaseNotificationToAll(String promoMessage) {
        Message message = Message.builder()
                .setTopic("allDevices")
                .setNotification(Notification.builder()
                        .setTitle("Promo Notification")
                        .setBody(promoMessage)
                        .build())
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            System.out.println("Sent message to Firebase: " + promoMessage);
        } catch (Exception e) {
            System.err.println("Error sending message to Firebase: " + e.getMessage());
        }
    }
}
