package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.service.GoodsService;
import com.bbongdoo.doo.service.ProductsService;
import com.bbongdoo.doo.service.TestService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. Goods"})
@RestController
@RequestMapping("indexer")
@RequiredArgsConstructor
public class GoodsRestController {

    private final GoodsService goodsService;

    @CrossOrigin("*")
    @PostMapping("static/goods")
    public void staticIndex() {
        goodsService.staticIndex();
    }

}
