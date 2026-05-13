package com.doo.aqqle.model.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonRequest implements Request{

    @ApiParam(value = "from", defaultValue = "0")
    private int from;

    @ApiParam(value = "size", defaultValue = "10")
    private int size;
}
