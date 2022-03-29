package com.bbongdoo.doo.controller;



import com.bbongdoo.doo.dto.CrawlerDto;
import com.bbongdoo.doo.service.CrawlerService;
import com.bbongdoo.doo.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. 수집"})
@RestController
@RequestMapping("crawler")
@RequiredArgsConstructor
public class GoodsRestController {

    private final GoodsService goodsService;

    @ApiOperation(value = "수집", notes = "데이타 크롤링")
    @CrossOrigin("*")
    @PostMapping("goods")
    public void getData(
            @ApiParam(value = "keyword") @RequestParam(value = "keyword", defaultValue = "", required = true) String keyword
    ) {
        goodsService.getData(CrawlerDto.create(keyword));
    }
}
