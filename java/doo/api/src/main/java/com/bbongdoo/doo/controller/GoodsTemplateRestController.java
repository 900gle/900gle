package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.GoodsTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "5. Goods Template Apis")
@RequestMapping("api/template")
@RequiredArgsConstructor
public class GoodsTemplateRestController {

    private final GoodsTemplateService goodsTemplateService;

    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "검색")
    @GetMapping("goods")
    public CommonResult getDatas(
            @ApiParam(value = "검색어") @RequestParam(value = "searchWord", defaultValue = "나이키", required = true) @Validated final String searchWord
    ) {
        return goodsTemplateService.getProducts(searchWord);
    }

//    @CrossOrigin("*")
//    @ApiOperation(value = "search", notes = "검색")
//    @PostMapping("template")
//    public CommonResult postTemplate() {
//        return goodsService.postTemplate();
//    }
}
