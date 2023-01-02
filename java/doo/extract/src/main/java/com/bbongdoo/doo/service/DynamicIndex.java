package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.GoodsTextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

    public void startDynamic(){

//        IndexFileService.createFile("/data/indextime.txt");

        log.info("count : {} ",goodsTextRepository.count());

        for (int i = 0; i < 10; i++) {

            CompletableFuture<Integer> completableFuture = asyncTaskService.task(i);
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
