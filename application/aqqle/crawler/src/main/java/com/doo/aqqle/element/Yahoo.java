package com.doo.aqqle.element;

import org.springframework.web.util.UriComponentsBuilder;

public class Yahoo extends Site<Integer, Integer> {

    private static final String URL = "https://finance.yahoo.com/markets/stocks/most-active/";
    private static final String LIST_CSS = "table.markets-table>tbody>tr";

    @Override
    public String getUrl(Integer start, Integer count) {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("start", start)
                .queryParam("count", count)
                .build().toString();
    }

    public String getListCssSelector(){
        return LIST_CSS;
    }

}



