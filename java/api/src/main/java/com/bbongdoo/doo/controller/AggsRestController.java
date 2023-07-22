package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.AggsService;
import com.bbongdoo.doo.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "8. Aggs Apis")
@RequestMapping("api/search")
@RequiredArgsConstructor
public class AggsRestController {

    private final AggsService aggsService;

    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "aggregation")
    @GetMapping("aggs")
    public CommonResult getDatas() {
        return aggsService.getProducts();
    }
}
