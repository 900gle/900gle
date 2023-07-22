//package com.bbongdoo.doo.cron;
//
//import com.bbongdoo.doo.service.ProducerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class GoodsCron {
//
//    private final ProducerService producerService;
//
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void indexJob() {
//        producerService.dynamicIndex();
//    }
//}
