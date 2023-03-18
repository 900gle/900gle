package com.bbongdoo.doo.base.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlerConfig {

    public static final String CRAWLER_COMMON_PROPERTIES = "crawlerCommonProperties";
    public static final String CRAWLER_HYPER_PROPERTIES = "crawlerHyperProperties";
    public static final String CRAWLER_DS_PROPERTIES = "crawlerDsProperties";
    public static final String CRAWLER_EXP_PROPERTIES = "crawlerExpProperties";
    public static final String CRAWLER_AURORA_PROPERTIES = "crawlerAuroraProperties";

    @Bean(CRAWLER_COMMON_PROPERTIES)
    @ConfigurationProperties("crawler.common")
    public CrawlerProperties commonProperties() {
        return new CrawlerProperties();
    }

    @Bean(CRAWLER_HYPER_PROPERTIES)
    @ConfigurationProperties("crawler.item.hyper")
    public CrawlerItemProperties hyperProperties() {
        return new CrawlerItemProperties();
    }

    @Bean(CRAWLER_DS_PROPERTIES)
    @ConfigurationProperties("crawler.item.ds")
    public CrawlerItemProperties dsProperties() {
        return new CrawlerItemProperties();
    }

    @Bean(CRAWLER_EXP_PROPERTIES)
    @ConfigurationProperties("crawler.item.exp")
    public CrawlerItemProperties expProperties() {
        return new CrawlerItemProperties();
    }

    @Bean(CRAWLER_AURORA_PROPERTIES)
    @ConfigurationProperties("crawler.item.aurora")
    public CrawlerItemProperties auroraProperties() {
        return new CrawlerItemProperties();
    }
}