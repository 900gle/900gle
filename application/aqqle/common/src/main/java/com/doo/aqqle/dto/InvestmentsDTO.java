package com.doo.aqqle.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvestmentsDTO {
    private Long id;
    private String company;
    private String exchange;
    private String type;
    private Long price;
    private Long quantity;
    private String useYn;
    private String createdTime;
    private String updatedTime;
}
