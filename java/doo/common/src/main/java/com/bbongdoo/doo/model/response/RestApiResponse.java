package com.bbongdoo.doo.model.response;

import com.bbongdoo.doo.model.response.CommonResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiResponse<T> extends CommonResult {

    private Long totalCount;
    private T data;

    public RestApiResponse(){
    }

    public RestApiResponse(T data){
        this.success = true;
        this.data = data;
        this.code = 200;
    }

    public RestApiResponse(Long totalCount, T data) {
        this.success = true;
        this.totalCount = totalCount;
        this.data = data;
        this.code = 200;
    }

    public RestApiResponse(Exception e) {
        this.success = false;
        this.code = 500;
    }

}
