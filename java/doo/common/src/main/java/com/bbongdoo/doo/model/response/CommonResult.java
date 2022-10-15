package com.bbongdoo.doo.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공 여부 : true / false")
    protected boolean success;

    @ApiModelProperty(value = "응답 코드 번호 : int")
    protected int code;

    @ApiModelProperty(value = "응답 메시지 : true / false")
    protected String msg;


}
