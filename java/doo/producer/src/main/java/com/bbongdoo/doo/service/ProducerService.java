package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.Goods;
import com.bbongdoo.doo.domain.GoodsRepository;
import com.bbongdoo.doo.dto.Prod;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final GoodsRepository goodsRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "900gle";

    public void dynamicIndex() {



        List<Goods> goodsList = null;
        goodsList = goodsRepository.findAll();
        if (goodsList.size() > 0) {
            goodsList.forEach(
                    x -> {



                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            String st = objectMapper.writeValueAsString(x);

                            System.out.println(st);

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }


                        System.out.println(String.format("Produce message : %s", x.getName()));
                        this.kafkaTemplate.send(TOPIC, x.getName());
                    }
            );
        } else {
            System.out.println("end");
        }
    }
}
