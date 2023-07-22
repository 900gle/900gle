package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.GoodsTextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicIndex {

    private final GoodsTextRepository goodsTextRepository;

    private final AsyncTaskService asyncTaskService;

    private List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();


    public void staticIndex(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String directory = "/data/static/"+LocalDateTime.now().format(dateTimeFormatter).toString();

        IndexFileService.createDirectory(directory);

        long count = goodsTextRepository.count();
        int chunk = 500;
        double endPage = Math.ceil(count / chunk);

        for (int i = 0; i < endPage +1; i++) {

            CompletableFuture<Integer> completableFuture = asyncTaskService.task(i, chunk, directory);
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
        log.info("End info ");

    }


    public void dynamicIndex(){

        long count = goodsTextRepository.count();
        int chunk = 10;
        double endPage = Math.ceil(count /chunk);

        for (int i = 0; i < endPage +1; i++) {

            CompletableFuture<Integer> completableFuture = asyncTaskService.task(i, chunk, "/data/");
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
        log.info("End info ");

    }






}
