package com.bbongdoo.doo.controller;

import com.bbongdoo.doo.model.response.CommonResult;
import com.bbongdoo.doo.service.OpencvImageSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Api(tags = "3. Products Apis")
@RequestMapping("api/images")
@RequiredArgsConstructor
public class ImagesRestController {

    private final OpencvImageSearchService opencvImageSearchService;

    @CrossOrigin("*")
    @ApiOperation(value = "index", notes = "이미지 검색 - 이미지 데이타 검색")
    @PostMapping("opencv")
    public CommonResult staticIndexer(
            @ApiParam(value = "파일") @RequestParam(value = "file", required = true) @Validated final MultipartFile file
    ) {

        return opencvImageSearchService.getImages(com.etoos.imagesearch.dto.ImageSearchDTO.builder().file(file).build());
    }

}
