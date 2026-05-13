package com.doo.aqqle.portal.controller;


import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.LocationRequest;
import com.doo.aqqle.model.request.ShopRequest;
import com.doo.aqqle.portal.service.PortalService;
import com.doo.aqqle.portal.service.RetryTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(tags = "5. Portal Apis")
@RequestMapping("api/portal")
@RequiredArgsConstructor
public class PortalRestController {

    private final PortalService service;
    private final RetryTestService retryTestService;

    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "검색")
    @GetMapping("search")
    public CommonResult getDatas(
    ) {
        LocationRequest locationRequest = new LocationRequest();
        ShopRequest shopRequest = new ShopRequest();
        return service.portalInfos(shopRequest, locationRequest);
    }



    @CrossOrigin("*")
    @ApiOperation(value = "retry", notes = "retry")
    @GetMapping("retry")
    public void retry(
    ) {
        retryTestService.start();
    }
}
