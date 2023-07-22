package com.bbongdoo.doo.component;

import org.springframework.web.util.UriComponentsBuilder;

public class Naver extends Site {

    private static final String URL = "https://search.shopping.naver.com/search/all";

    @Override
    public String getUrl(String k, int i) {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("frm", "NVSHATC")
                .queryParam("origQuery", k)
                .queryParam("pagingIndex", i)
                .queryParam("pagingSize", 40)
                .queryParam("productSet", "total")
                .queryParam("query", k)
                .queryParam("sort", "rel")
                .queryParam("timestamp", "")
                .queryParam("viewType", "list")
                .build().toString();
    }



}
