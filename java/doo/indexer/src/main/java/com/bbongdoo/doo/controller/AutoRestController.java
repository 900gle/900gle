package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.service.AutoService;
import com.bbongdoo.doo.service.GoodsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"3. Auto Complate"})
@RestController
@RequestMapping("indexer")
@RequiredArgsConstructor
public class AutoRestController {

    private final AutoService autoService;

    @CrossOrigin("*")
    @PostMapping("static/auto")
    public void staticIndex() {
        autoService.staticIndex();
    }

}
