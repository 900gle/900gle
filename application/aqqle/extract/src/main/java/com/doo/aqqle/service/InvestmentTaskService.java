package com.doo.aqqle.service;


import com.doo.aqqle.dto.*;
import com.doo.aqqle.repository.Goods;
import com.doo.aqqle.repository.GoodsRepository;
import com.doo.aqqle.repository.Investments;
import com.doo.aqqle.repository.InvestmentsRepository;
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
public class InvestmentTaskService {

    private final InvestmentsRepository investmentsRepository;

    @Async("executor")
    public CompletableFuture<Integer> task(int i, int chunk, String directory) {
        PageRequest pageRequest = PageRequest.of(i, chunk);
        Page<Investments> investments = investmentsRepository.findAllByOrderByIdAsc(pageRequest);
        List<InvestmentsDTO> investmentsDTOList = new ArrayList<>();

        try {
            investments.stream().forEach(
                    x -> {
                        investmentsDTOList.add(
                                InvestmentsDTO.builder()
                                        .id(x.getId())
                                        .company(x.getCompany())
                                        .exchange(x.getExchange())
                                        .price(x.getPrice())
                                        .quantity(x.getQuantity())
                                        .useYn(x.getUseYn())
                                        .build()
                        );
                    }
            );

            ObjectMapper objectMapper = new ObjectMapper();

            InvestmentListDTO investmentListDTO = new InvestmentListDTO();
            investmentListDTO.setInvestmentsDTOList(investmentsDTOList);


            String goodsJson = objectMapper.writeValueAsString(investmentListDTO);
            IndexFileService.createFile(directory + "/indextime_" + i + ".txt", goodsJson);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return CompletableFuture.supplyAsync(investments::getSize);
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
