package com.doo.aqqle.logger.controller;


import com.doo.aqqle.dto.KeyWordRequest;
import com.doo.aqqle.service.DictionarysService;
import com.doo.aqqle.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "1. Dictionarys Apis")
@RequestMapping("manage")
@RequiredArgsConstructor
public class LoggerRestController {

    private final DictionarysService dictionarysService;
    private final ResponseService responseService;

    @ApiOperation(value = "키워드 저장", notes = "키워드 저장")
    @CrossOrigin("*")
    @PostMapping("logger")
    public void postData(@ApiParam(value = "words") @RequestBody KeyWordRequest keyWordRequest) {
        dictionarysService.post(keyWordRequest);
    }
}
