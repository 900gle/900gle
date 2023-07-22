package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.service.ResponseService;
import com.bbongdoo.doo.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. 게임장"})
@RestController
@RequestMapping("manage")
@RequiredArgsConstructor
public class GamesRestController {

    private final UsersService usersService;
    private final ResponseService responseService;

    @ApiOperation(value = "도박꾼 저장", notes = "도박꾼 저장")
    @CrossOrigin("*")
    @PostMapping("games")
    public void postData(@ApiParam(value = "name") @RequestParam(value = "name", defaultValue = "", required = true) String name) {
        usersService.post(name);
    }

//    @ApiOperation(value = "키워드 조회", notes = "키워드 조회")
//    @CrossOrigin("*")
//    @GetMapping("keywords")
//    public CommonResult getData() {
//        return responseService.getSingleResult(usersService.get());
//    }
//
//    @ApiOperation(value = "키워드 업데이트", notes = "키워드 업데이트")
//    @CrossOrigin("*")
//    @PutMapping("keywords")
//    public CommonResult putData(
//            @ApiParam(value = "id") @RequestParam(value = "id", defaultValue = "", required = true) Long id,
//            @ApiParam(value = "useYn") @RequestParam(value = "useYn", defaultValue = "", required = true) String useYn
//    ) {
//        return responseService.getSingleResult(usersService.put(id, useYn));
//    }


}
