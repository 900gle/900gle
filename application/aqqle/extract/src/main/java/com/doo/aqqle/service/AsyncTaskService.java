package com.doo.aqqle.service;


import com.doo.aqqle.dto.ExtractGoodsTextDTO;
import com.doo.aqqle.dto.GoodTextDTO;
import com.doo.aqqle.repository.Goods;
import com.doo.aqqle.repository.GoodsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncTaskService {

    private final GoodsRepository goodsRepository;

    @Async("executor")
    public CompletableFuture<Integer> task(int i, int chunk, String directory) {
        PageRequest pageRequest = PageRequest.of(i, chunk);
        Page<Goods> goodsTexts = goodsRepository.findAllByOrderByIdAsc(pageRequest);
        List<ExtractGoodsTextDTO> extractGoodsTextDTOList = new ArrayList<>();

        try {
            goodsTexts.stream().forEach(
                    x -> {
                        extractGoodsTextDTOList.add(
                                ExtractGoodsTextDTO.builder()
                                        .id(x.getId())
                                        .keyword(x.getKeyword())
                                        .name(x.getName())
                                        .brand(x.getBrand())
                                        .price(x.getPrice())
                                        .category(x.getCategory())
                                        .category1(x.getCategory1())
                                        .category2(x.getCategory2())
                                        .category3(x.getCategory3())
                                        .category4(x.getCategory4())
                                        .category5(x.getCategory5())
                                        .image(x.getImage())
                                        .featureVector(getFeaturePase(x.getFeatureVector()))
                                        .weight(x.getWeight())
                                        .popular(x.getPopular())
                                        .type(x.getType())
                                        .createdTime(x.getCreatedTime())
                                        .updatedTime(x.getUpdatedTime())
                                        .build()
                        );
                    }
            );

            ObjectMapper objectMapper = new ObjectMapper();
            GoodTextDTO goodTextDTO = new GoodTextDTO();
            goodTextDTO.setExtractGoodsTextDTOList(extractGoodsTextDTOList);
            String goodsJson = objectMapper.writeValueAsString(goodTextDTO);
            IndexFileService.createFile(directory + "/indextime_" + i + ".txt", goodsJson);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return CompletableFuture.supplyAsync(goodsTexts::getSize);
    }

    private List<Double> getFeaturePase(String s) {
        List<Double> ret = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(s);
            return array;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
