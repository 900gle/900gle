package com.bbongdoo.doo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


@Service
public class GoodsAsyncService {

    public static void main(String[] args) {

//        new Thread(() -> {
//            try {
//                CompletableFuture
//                        .supplyAsync(Work::work1)
//                        .thenAccept(Work::work2)
//                        .get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        Work.work3();


        CompletableFuture<String> future
                = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(2000);
            future.complete("Finished");

            return null;
        });

        try {
            System.out.println(future.get());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



}



