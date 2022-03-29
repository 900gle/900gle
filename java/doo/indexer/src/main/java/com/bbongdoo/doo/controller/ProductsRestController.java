package com.bbongdoo.doo.controller;


import com.bbongdoo.doo.service.ProductsService;
import com.bbongdoo.doo.service.TestService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1. Products"})
@RestController
@RequestMapping("indexer")
@RequiredArgsConstructor
public class ProductsRestController {

    private final ProductsService productsService;
    private final TestService testService;

    @CrossOrigin("*")
    @PostMapping("static/products")
    public void staticIndex() {
        productsService.staticIndex();
    }

    @CrossOrigin("*")
    @PostMapping("file/products")
    public void testfile() {
        testService.staticIndex();
    }
}
