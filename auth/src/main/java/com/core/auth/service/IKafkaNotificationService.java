package com.core.auth.service;

import org.springframework.beans.factory.annotation.Autowired;

public interface IKafkaNotificationService {

    void sendNotification(String message);
}
