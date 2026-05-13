package com.doo.aqqle.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationRequest extends CommonRequest implements Request{

    @ApiParam(value = "거리 km", defaultValue = "5")
    private String distance;

    @ApiParam(value = "국가코드", defaultValue = "KR")
    private String countryCode;
}
