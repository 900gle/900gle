package com.bbongdoo.doo.cron;

import com.bbongdoo.doo.service.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndexCron {

//    private final IndexService indexService;
//
//    @Scheduled(cron = "0/1 * * * * *")
//    public void indexJob() {
//        indexService.dynamicIndex();
//    }
}
