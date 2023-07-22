package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.GoodsText;
import com.bbongdoo.doo.domain.GoodsTextRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final GoodsTextRepository goodsTextRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "900gle";

    public void dynamicIndex() {

        List<GoodsText> goodsList = null;
//        goodsList = goodsTextRepository.findAll();
        goodsList = goodsTextRepository.findAllById(new Iterable<Long>() {
            @Override
            public Iterator<Long> iterator() {

                long[] longs = new long[3];
                longs[0] = 1;
                longs[1] = 2;
                longs[2] = 3;

                return Arrays.stream(longs).iterator();
            }
        });
        if (goodsList.size() > 0) {
            goodsList.forEach(
                    x -> {
                        String st = "";
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                             st = objectMapper.writeValueAsString(x);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        System.out.println(String.format("Produce message : %s", st));
                        this.kafkaTemplate.send(TOPIC, st);
                    }
            );
        } else {
            System.out.println("end");
        }
    }
}
