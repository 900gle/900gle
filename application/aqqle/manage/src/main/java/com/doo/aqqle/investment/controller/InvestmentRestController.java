package com.doo.aqqle.investment.controller;


import com.doo.aqqle.model.stock.InvestmentRequest;
import com.doo.aqqle.investment.service.InvestmentService;
import com.doo.aqqle.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "2. Investment Apis")
@RequestMapping("manage")
@RequiredArgsConstructor
public class InvestmentRestController {

    private final InvestmentService service;
    private final ResponseService responseService;

    @ApiOperation(value = "키워드 저장", notes = "키워드 저장")
    @CrossOrigin("*")
    @PostMapping("investment")
    public void postData(@ModelAttribute InvestmentRequest request) {
        service.post(request);
    }
//
//    @ApiOperation(value = "키워드 조회", notes = "키워드 조회")
//    @CrossOrigin("*")
//    @GetMapping("distionarys")
//    public CommonResult getData() {
//        return responseService.getSingleResult(dictionarysService.get());
//    }
//
//    @ApiOperation(value = "키워드 업데이트", notes = "키워드 업데이트")
//    @CrossOrigin("*")
//    @PutMapping("distionarys")
//    public CommonResult putData(
//            @ApiParam(value = "id") @RequestParam(value = "id", defaultValue = "", required = true) Long id,
//            @ApiParam(value = "useYn") @RequestParam(value = "useYn", defaultValue = "", required = true) String useYn
//    ) {
//        return responseService.getSingleResult(dictionarysService.put(id, useYn));
//    }


}
