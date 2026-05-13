package com.doo.aqqle.service;

import com.doo.aqqle.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "5amsung";

    public String postFinish(Game game) {
        this.kafkaTemplate.send(TOPIC, game.getName());
        return game.getName();
    }
}
