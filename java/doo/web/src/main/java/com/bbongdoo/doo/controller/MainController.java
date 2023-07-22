package com.bbongdoo.doo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("")
    public String main(Model model){

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("api", "http://localhost:8080");
        model.addAllAttributes(dataMap);

//        return "main";
        return "text";
    }

    @GetMapping("detail/{productId}")
    public String detail(Model model, @PathVariable String productId){

        System.out.println(productId);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("api", "http://localhost:8080");
        dataMap.put("productId", productId);
        model.addAllAttributes(dataMap);

        return "detail";
    }
}
