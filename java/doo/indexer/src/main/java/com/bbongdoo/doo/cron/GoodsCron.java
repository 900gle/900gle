package com.bbongdoo.doo.cron;

import com.bbongdoo.doo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsCron {

//    private final GoodsService goodsService;
//
//    @Scheduled(cron = "0/1 * * * * *")
//    public void indexJob() {
//        goodsService.dynamicIndex();
//    }
}
