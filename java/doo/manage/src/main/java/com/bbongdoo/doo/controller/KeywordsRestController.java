package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.KeywordsService;
import com.bbongdoo.doo.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"1. 키워드"})
@RestController
@RequestMapping("manage")
@RequiredArgsConstructor
public class KeywordsRestController {

    private final KeywordsService keywordsService;
    private final ResponseService responseService;

    @ApiOperation(value = "키워드 저장", notes = "키워드 저장")
    @CrossOrigin("*")
    @PostMapping("keywords")
    public void postData(@ApiParam(value = "keywords") @RequestParam(value = "keywords", defaultValue = "", required = true) List<String> keywords) {
        keywordsService.post(keywords);
    }

    @ApiOperation(value = "키워드 조회", notes = "키워드 조회")
    @CrossOrigin("*")
    @GetMapping("keywords")
    public CommonResult getData() {
        return responseService.getSingleResult(keywordsService.get());
    }

    @ApiOperation(value = "키워드 업데이트", notes = "키워드 업데이트")
    @CrossOrigin("*")
    @PutMapping("keywords")
    public CommonResult putData(
            @ApiParam(value = "id") @RequestParam(value = "id", defaultValue = "", required = true) Long id,
            @ApiParam(value = "useYn") @RequestParam(value = "useYn", defaultValue = "", required = true) String useYn
    ) {
        return responseService.getSingleResult(keywordsService.put(id, useYn));
    }
}
