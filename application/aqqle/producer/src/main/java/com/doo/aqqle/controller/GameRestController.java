//package com.doo.aqqle.controller;
//
//import com.doo.aqqle.model.Game;
//import com.doo.aqqle.service.GameService;
//import com.doo.aqqle.model.CommonResult;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@Api(tags = "1. Games Apis")
//@RequestMapping("game")
//@RequiredArgsConstructor
//public class GameRestController {
//
//    private final GameService gameService;
//
//
//    @CrossOrigin("*")
//    @ApiOperation(value = "search", notes = "검색")
//    @PostMapping("finish")
//    public CommonResult postDatas(
//            @ApiParam(value = "검색어")  @RequestBody Game game
//    ) {
//
//         gameService.postFinish(game);
//        return new CommonResult();
//    }
//
//}
