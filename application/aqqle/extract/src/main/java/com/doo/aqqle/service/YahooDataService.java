package com.doo.aqqle.service;

import com.doo.aqqle.annotation.Timer;
import com.doo.aqqle.repository.StockDataRepository;
import com.doo.aqqle.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service("YahooDataService")
public class YahooDataService extends ExtractService implements AqqleExtract {

    @Value("${app.extract-path}")
    private String extractPath;


    private final YahooDataTaskService yahooDataTaskService;
    private List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();


    public YahooDataService(StockRepository stockRepository, StockDataRepository stockDataRepository, YahooDataTaskService yahooDataTaskService) {
        super(stockRepository, stockDataRepository);
        this.yahooDataTaskService = yahooDataTaskService;
    }

    @Override
    @Timer
    public void execute() {

        String type = "yahoo";
        File file = new File(extractPath);

        if (file.exists() && file.isDirectory()) {
            try {
                FileUtils.cleanDirectory(file);
            } catch (IOException e) {
                log.error("Failed to clean directory: {}", extractPath, e);
            }

        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String directory = extractPath + LocalDateTime.now().format(dateTimeFormatter).toString();

        log.info("directory path : {}", directory);
        IndexFileService.createDirectory(directory);

        long count = stockDataRepository.count();
        int chunk = 500;
        double endPage = Math.ceil(count / chunk);

        for (int i = 0; i < endPage + 1; i++) {
            CompletableFuture<Integer> completableFuture = yahooDataTaskService.task(i, chunk, directory);
            completableFutures.add(completableFuture);
        }
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                completableFutures.toArray(new CompletableFuture[0])
        );
        try {
            allFutures.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted during task execution", e);
        } catch (ExecutionException e) {
            log.error("Task execution failed", e);
        }
    }
}

