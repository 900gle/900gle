package com.doo.aqqle.service;

import com.doo.aqqle.annotation.IndexerLog;

import com.doo.aqqle.repository.InvestmentsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    private final InvestmentsRepository investmentsRepository;
    private final InvestmentTaskService investmentTaskService;

    private List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();

    @IndexerLog
    public void index(String type) throws IOException {

        String extractPath = "/data/" + type + "/static/";

        File file = new File(extractPath);

        if (file.exists() && file.isDirectory()) {
            FileUtils.cleanDirectory(file);
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String directory = extractPath + LocalDateTime.now().format(dateTimeFormatter).toString();

        System.out.println(directory);
        IndexFileService.createDirectory(directory);
//
        long count = investmentsRepository.count();
        int chunk = 500;
        double endPage = Math.ceil(count / chunk);

        for (int i = 0; i < endPage + 1; i++) {
            CompletableFuture<Integer> completableFuture = investmentTaskService.task(i, chunk, directory);
            completableFutures.add(completableFuture);
        }

        for (CompletableFuture<Integer> future : completableFutures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
