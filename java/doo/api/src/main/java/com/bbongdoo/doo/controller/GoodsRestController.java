package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "4. Goods Apis")
@RequestMapping("api/search")
@RequiredArgsConstructor
public class GoodsRestController {

    private final GoodsService goodsService;

    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "검색")
    @GetMapping("goods")
    public CommonResult getDatas(
            @ApiParam(value = "검색어") @RequestParam(value = "searchWord", defaultValue = "나이키", required = true) @Validated final String searchWord
    ) {
        return goodsService.getProducts(searchWord);
    }

//    @CrossOrigin("*")
//    @ApiOperation(value = "search", notes = "검색")
//    @PostMapping("template")
//    public CommonResult postTemplate() {
//        return goodsService.postTemplate();
//    }
}
