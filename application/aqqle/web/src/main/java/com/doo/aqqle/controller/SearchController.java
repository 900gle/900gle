package com.doo.aqqle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchController {

    @GetMapping("search")
    public String main(Model model){

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("api", "http://localhost:8080");
        model.addAllAttributes(dataMap);

        return "search";
    }


}
