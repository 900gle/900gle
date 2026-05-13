package com.doo.aqqle.model.stock;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockManageRequest {

    @ApiParam(value = "company", defaultValue = "")
    private String company;

    @ApiParam(value = "company code", defaultValue = "")
    private String companyCode;

    @ApiParam(value = "exchange", defaultValue = "")
    private String exchange;

    @ApiParam(value = "type", defaultValue = "")
    private String type;

    @ApiParam(value = "start date", defaultValue = "")
    private String startDate;

    @ApiParam(value = "end date", defaultValue = "")
    private String endDate;

    @ApiParam(value = "useYn", defaultValue = "")
    private String useYn;
}
