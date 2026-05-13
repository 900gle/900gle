package com.doo.aqqle.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopRequest extends CommonRequest implements Request{

    @ApiParam(value = "검색어", defaultValue = "몽클레어")
    private String searchWord;

    @ApiParam(value = "유사도", defaultValue = "N")
    private String similarity;
}
