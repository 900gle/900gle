package com.bbongdoo.doo.base.core.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrawlerProperties {

    private int fileRowSize;
    private int bufferRowSize;
    private int dbLimitSize;
    private int groupLimitSize;
    private int remainSize;
    private int threadCount;
    private int maxThreadCount;
    private int queueCapacity;
    private int futureSize;
    private int dynamiclistRemainDays;
}
