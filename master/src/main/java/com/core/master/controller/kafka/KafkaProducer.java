package com.core.master.controller.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void writeMessage(String topic,String msg){
        this.kafkaTemplate.send(topic, msg);
    }

}

