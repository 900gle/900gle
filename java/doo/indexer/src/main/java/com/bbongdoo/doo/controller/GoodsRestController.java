package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.service.AsyncIndexService;
import com.bbongdoo.doo.service.GoodsService;
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

    private final AsyncIndexService asyncIndexService;

    @CrossOrigin("*")
    @PostMapping("static/goods")
    public void staticIndex() {
        asyncIndexService.staticIndex();
    }

}
