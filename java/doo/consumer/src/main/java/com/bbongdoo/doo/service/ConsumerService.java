package com.bbongdoo.doo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConsumerService {

    private static final String TOPIC = "900gle";
    private static final String GROUP_ID = "doo";

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consume(String message) throws IOException {
        System.out.println(String.format("Consumed message : %s", message));
    }
}
