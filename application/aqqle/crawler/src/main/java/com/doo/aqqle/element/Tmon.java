package com.doo.aqqle.element;

import org.springframework.web.util.UriComponentsBuilder;

public class Tmon extends Site<String, Integer> {

    private static final String URL = "https://search.tmon.co.kr/search/";

    @Override
    public String getUrl(String k, Integer i) {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("keyword", k)
                .queryParam("page", i)
                .queryParam("thr", "hs")
                .build().toString();
    }

    @Override
    public String getListCssSelector() {
        return null;
    }
}
