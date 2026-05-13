package com.doo.aqqle.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ListResult<T> extends CommonResult implements Serializable {
    private List<T> list;
}
