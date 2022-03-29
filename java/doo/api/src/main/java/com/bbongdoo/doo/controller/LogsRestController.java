package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.LogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "2. Logs Apis")
@RequestMapping("api/")
@RequiredArgsConstructor
public class LogsRestController {

    private final LogsService logsService;

    @CrossOrigin("*")
    @ApiOperation(value = "logs", notes = "로그")
    @GetMapping("logs")
    public CommonResult getDatas(
            @ApiParam(value = "검색어") @RequestParam(value = "searchWord", defaultValue = "", required = true) @Validated final String searchWord
    ) {
        return logsService.getProducts(searchWord);
    }
}
