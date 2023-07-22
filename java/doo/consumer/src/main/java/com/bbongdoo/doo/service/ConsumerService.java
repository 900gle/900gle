package com.bbongdoo.doo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumerService {


    private final GoodsService goodsService;

    private static final String TOPIC = "900gle";
    private static final String GROUP_ID = "doo";


    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void consume(String message) throws IOException {
        List<String> messageList = new ArrayList<>();
        messageList.add(message);

            goodsService.staticIndex(messageList);
            messageList.clear();

    }
}
