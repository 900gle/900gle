package com.bbongdoo.doo.cron;

import com.bbongdoo.doo.service.AsyncIndexService;
import com.bbongdoo.doo.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoodsCron {

//    private final GoodsService goodsService;
    private final AsyncIndexService asyncIndexService;

    @Scheduled(cron = "0 0/1 * * * *")
    public void indexJob() {
        asyncIndexService.staticIndex();
//        asyncIndexService.dynamicIndex();
//        goodsService.dynamicIndex();
    }
}




