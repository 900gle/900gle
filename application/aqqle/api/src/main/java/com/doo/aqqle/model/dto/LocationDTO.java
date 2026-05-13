package com.doo.aqqle.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO {

    @ApiModelProperty(value = "사용자 디바이스 유형 (PC, MWEB, APP)", hidden = false)
    private String distance;

    @ApiModelProperty(value = "사용자 디바이스 유형 (PC, MWEB, APP)", hidden = false)
    private String countryCode;
}
