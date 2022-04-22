package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.service.KeywordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. 수집"})
@RestController
@RequestMapping("manage")
@RequiredArgsConstructor
public class KeywordsRestController {

    private final KeywordsService keywordsService;

    @ApiOperation(value = "키워드 저장", notes = "키워드 저장")
    @CrossOrigin("*")
    @PostMapping("keywords")
    public void getData(
            @ApiParam(value = "keywords") @RequestParam(value = "keywords", defaultValue = "", required = true) List<String> keywords
    ) {
        keywordsService.post(keywords);
    }
}
