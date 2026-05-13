package com.doo.aqqle.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeRequest extends CommonRequest implements Request{

    @ApiParam(value = "Action", defaultValue = "I")
    private String action;

    @ApiParam(value = "상품번호", defaultValue = "100000001")
    private String productNo;
}
