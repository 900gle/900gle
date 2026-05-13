package com.doo.aqqle.model.stock;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvestmentRequest {

    @ApiParam(value = "company", defaultValue = "")
    private String company;

    @ApiParam(value = "exchange", defaultValue = "")
    private String exchange;

    @ApiParam(value = "type", defaultValue = "")
    private String type;

    @ApiParam(value = "price", defaultValue = "")
    private Long price;

    @ApiParam(value = "quantity", defaultValue = "")
    private Long quantity;

    @ApiParam(value = "useYn", defaultValue = "")
    private String useYn;
}
