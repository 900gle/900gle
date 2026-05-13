package com.doo.aqqle.service;

import com.doo.aqqle.domain.Users;
import com.doo.aqqle.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "aqqle";

    public String postFinish(Users users) {

        this.kafkaTemplate.send(TOPIC, users.getName());
        System.out.println(users.getName());
        return users.getName();
    }
}
