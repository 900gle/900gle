package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.AutoService;
import com.bbongdoo.doo.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "4. Auto Apis")
@RequestMapping("api/auto")
@RequiredArgsConstructor
public class AutoRestController {

    private final AutoService autoService;

    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "검색")
    @GetMapping("")
    public CommonResult getDatas(
            @ApiParam(value = "검색어") @RequestParam(value = "searchWord", defaultValue = "나이키", required = true) @Validated final String searchWord
    ) {
        return autoService.getProducts(searchWord);
    }

}
