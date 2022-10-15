package com.bbongdoo.doo.model.response;

import com.bbongdoo.doo.model.response.CommonResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    protected T data;
}
