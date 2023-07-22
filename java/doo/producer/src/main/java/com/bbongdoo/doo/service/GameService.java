package com.bbongdoo.doo.service;

import com.bbongdoo.doo.model.Game;
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
