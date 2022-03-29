package com.bbongdoo.doo.controller;



import com.bbongdoo.doo.dto.CrawlerDto;
import com.bbongdoo.doo.service.CrawlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. 수집"})
@RestController
@RequestMapping("crawler")
@RequiredArgsConstructor
public class CrawlerRestController {

    private final CrawlerService crawlerService;

    @ApiOperation(value = "수집", notes = "네이버 쇼핑 데이타 수")
    @CrossOrigin("*")
    @PostMapping("products")
    public void getData(
            @ApiParam(value = "keyword") @RequestParam(value = "keyword", defaultValue = "", required = true) String keyword
    ) {
        crawlerService.getData(CrawlerDto.create(keyword));
    }
}
