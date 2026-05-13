package com.doo.aqqle.element;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class YahooData extends Site<String, Map<String, Long>> {

    private static final String URL = "https://finance.yahoo.com/quote/";
    private static final String LIST_CSS = "table.yf-ewueuo>tbody>tr.yf-ewueuo";


    @Override
    public String getUrl(String companyCode, Map period) {
        return UriComponentsBuilder.fromHttpUrl(URL + companyCode + "/history/")
                .queryParam("period1", period.get("period1"))
                .queryParam("period2", period.get("period2"))
                .build().toString();
    }

    public String getListCssSelector(){
        return LIST_CSS;
    }

}



