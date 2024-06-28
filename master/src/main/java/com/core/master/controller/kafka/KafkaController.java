package com.core.master.controller.kafka;


import com.core.master.dto.kafka.KafkaTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-login/kafka/")
public class KafkaController {

    private final KafkaProducer producer;

    @Autowired
    public KafkaController(KafkaProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/publish")
    public void writeMessageToTopic(@RequestBody KafkaTopic kafkaTopic) {
        this.producer.writeMessage(kafkaTopic.getTopic(), kafkaTopic.getMessage());
        // You can add additional logic here if needed
    }

}
