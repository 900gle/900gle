package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.KeywordsService;
import com.bbongdoo.doo.service.ResponseService;
import com.bbongdoo.doo.service.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"3. 사용자"})
@RestController
@RequestMapping("manage")
@RequiredArgsConstructor
public class UsersRestController {

    private final UsersService usersService;
    private final ResponseService responseService;

    @ApiOperation(value = "도박꾼 저장", notes = "도박꾼 저장")
    @CrossOrigin("*")
    @PostMapping("users")
    public void postData(@ApiParam(value = "name") @RequestParam(value = "name", defaultValue = "", required = true) String name) {
        usersService.post(name);
    }

    @ApiOperation(value = "도박꾼 조회", notes = "도박꾼 조회")
    @CrossOrigin("*")
    @GetMapping("users")
    public CommonResult getData() {
        return responseService.getSingleResult(usersService.get());
    }
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
