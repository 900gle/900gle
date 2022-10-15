//package com.bbongdoo.doo.controller;
//
//
//import com.bbongdoo.doo.service.IndexService;
//import com.bbongdoo.doo.service.LocationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("indexer")
//@RequiredArgsConstructor
//public class IndexerRestController {
//
//    private final IndexService indexService;
//
//    private final LocationService locationService;
//
//
//    @CrossOrigin("*")
//    @PostMapping("static/product")
//    public void staticIndex() {
//        indexService.staticIndex();
//    }
//
//    @CrossOrigin("*")
//    @PostMapping("static/location")
//    public void locationIndex() {
//        locationService.staticIndex();
//    }
//
////    @CrossOrigin("*")
////    @PostMapping("static/category")
////    public void category() {
////        categoryService.getCategory();
////    }
//}
