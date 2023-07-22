package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.Game;
import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "1. Games Apis")
@RequestMapping("game")
@RequiredArgsConstructor
public class GameRestController {

    private final GameService gameService;


    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "검색")
    @PostMapping("finish")
    public CommonResult postDatas(
            @ApiParam(value = "검색어")  @RequestBody Game game
    ) {

         gameService.postFinish(game);
        return new CommonResult();
    }

}
