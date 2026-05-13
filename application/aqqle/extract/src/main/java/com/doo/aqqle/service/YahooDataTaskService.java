package com.doo.aqqle.service;

import com.doo.aqqle.dto.StockDataDTO;
import com.doo.aqqle.dto.StockDataListDTO;
import com.doo.aqqle.repository.StockData;
import com.doo.aqqle.repository.StockDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service("YahooDataAsyncService")
@RequiredArgsConstructor
public class YahooDataTaskService {

    private final StockDataRepository stockDataRepository;
    @Async("executor")
    public CompletableFuture<Integer> task(int i, int chunk, String directory) {
        PageRequest pageRequest = PageRequest.of(i, chunk);
        Page<StockData> stockDatas = stockDataRepository.findAllByOrderByIdAsc(pageRequest);
        List<StockDataDTO> stockDataDTOList = new ArrayList<>();

        try {
            stockDatas.stream().forEach(
                    x -> {
                        stockDataDTOList.add(
                                StockDataDTO.builder()
                                        .id(x.getId())
                                        .company(x.getCompany())
                                        .companyCode(x.getCompanyCode())
                                        .tradingDate(x.getTradingDate())
                                        .open(x.getOpen())
                                        .high(x.getHigh())
                                        .low(x.getLow())
                                        .close(x.getClose())
                                        .adjClose(x.getAdjClose())
                                        .volume(x.getVolume())
                                        .build()
                        );
                    }
            );

            ObjectMapper objectMapper = new ObjectMapper();

            StockDataListDTO stockDataListDTO = new StockDataListDTO();
            stockDataListDTO.setStockDataDTOList(stockDataDTOList);

            String goodsJson = objectMapper.writeValueAsString(stockDataListDTO);
            IndexFileService.createFile(directory + "/indextime_" + i + ".txt", goodsJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("TASK");
        return CompletableFuture.supplyAsync(stockDatas::getSize);
    }
}
