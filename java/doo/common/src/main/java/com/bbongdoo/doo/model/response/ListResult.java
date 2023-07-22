package com.bbongdoo.doo.model.response;

import com.bbongdoo.doo.model.response.CommonResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> extends CommonResult {
    private List<T> list;
}
