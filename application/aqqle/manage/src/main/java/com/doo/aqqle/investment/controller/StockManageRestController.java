package com.doo.aqqle.investment.controller;


import com.doo.aqqle.advice.exception.CApiFailException;
import com.doo.aqqle.investment.service.ExceptionForceService;
import com.doo.aqqle.investment.service.InvestmentService;
import com.doo.aqqle.investment.service.StockManageService;
import com.doo.aqqle.model.stock.StockManageRequest;
import com.doo.aqqle.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "3. Stock Apis")
@RequestMapping("manage")
@RequiredArgsConstructor
public class StockManageRestController {

    private final StockManageService stockManageService;
    private final ResponseService responseService;
    private final ExceptionForceService exceptionForceService;

    @ApiOperation(value = "키워드 저장", notes = "키워드 저장")
    @CrossOrigin("*")
    @PostMapping("stock")
    public void postData(@ModelAttribute StockManageRequest request) {
        stockManageService.post(request);
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


    @ApiOperation(value = "익셉션", notes = "익셉션")
    @CrossOrigin("*")
    @PostMapping("exception")
    public void apiFail() {

            exceptionForceService.exceptionExec();

    }

}
