package com.doo.aqqle.shop.controller;


import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.ShopRequest;
import com.doo.aqqle.portal.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "1. Shop APIs")
@RequestMapping("api/search")
@RequiredArgsConstructor
public class ShopRestController {

    private final ShopService service;

    @CrossOrigin("*")
    @ApiOperation(value = "Search products", notes = "검색 조건에 따라 상품 목록을 조회합니다.")
    @GetMapping("/shops")
    public ResponseEntity<CommonResult> getDatas(
            @ModelAttribute ShopRequest request
    ) {
        CommonResult result = service.getProducts(request);
        return ResponseEntity.ok(result);
    }
}

