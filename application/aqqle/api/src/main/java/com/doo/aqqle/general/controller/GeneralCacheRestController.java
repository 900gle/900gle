package com.doo.aqqle.general.controller;


import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.LikeRequest;
import com.doo.aqqle.portal.service.GeneralCacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "4. General Apis")
@RequestMapping("api/cache")
@RequiredArgsConstructor
public class GeneralCacheRestController {

    private final GeneralCacheService service;

    @CrossOrigin("*")
    @ApiOperation(value = "search", notes = "검색")
    @GetMapping("like")
    public CommonResult getDatas(
            @ModelAttribute LikeRequest request
    ) {
        return service.likeCache(request);
    }

}
