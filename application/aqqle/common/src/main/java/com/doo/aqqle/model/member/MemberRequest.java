package com.doo.aqqle.model.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberRequest {

    @ApiParam(value = "userId", defaultValue = "")
    private String userId;

    @ApiParam(value = "name", defaultValue = "")
    private String name;

    @ApiParam(value = "useYn", defaultValue = "Y")
    private String useYn;
}
