package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "1. Products Apis")
@RequestMapping("api")
@RequiredArgsConstructor
public class ProductsRestController {

    private final ProductsService productsService;

    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "검색")
    @GetMapping("search")
    public CommonResult getDatas(
            @ApiParam(value = "검색어") @RequestParam(value = "searchWord", defaultValue = "나이키", required = true) @Validated final String searchWord
    ) {
        return productsService.getProducts(searchWord);
    }


}
