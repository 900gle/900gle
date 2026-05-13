package com.doo.aqqle.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockDataDTO {
    private Long id;
    private String company;
    private String companyCode;
    private String tradingDate;
    private float open;
    private float high;
    private float low;
    private float close;
    private float adjClose;
    private long volume;
}
