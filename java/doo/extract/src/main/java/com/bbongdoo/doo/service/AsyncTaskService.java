package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.GoodsText;
import com.bbongdoo.doo.domain.GoodsTextRepository;
import com.bbongdoo.doo.dto.ExtractGoodsTextDTO;
import com.bbongdoo.doo.dto.GoodTextDTO;
import com.bbongdoo.doo.utils.ParseVector;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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

    private final GoodsTextRepository goodsTextRepository;

    @Async("executor")
    public CompletableFuture<Integer> task(int i, int chunk, String directory) {
        int start = i * chunk;
        int end = (i + 1) * chunk;

        PageRequest pageRequest = PageRequest.of(start, chunk);
        Page<GoodsText> goodsTexts = goodsTextRepository.findAllByOrderByIdAsc(pageRequest);
        List<ExtractGoodsTextDTO> extractGoodsTextDTOList = new ArrayList<>();

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
//                                    .featureVector(x.getFeatureVector())
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

        try {
            String goodsJson = objectMapper.writeValueAsString(goodTextDTO);
            IndexFileService.createFile(directory + "/indextime_" + i + ".txt", goodsJson);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return CompletableFuture.supplyAsync(goodsTexts::getSize);
    }

}
