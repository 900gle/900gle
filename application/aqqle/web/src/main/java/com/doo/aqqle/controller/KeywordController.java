package com.doo.aqqle.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("keyword")
@RequiredArgsConstructor
public class KeywordController {

    @GetMapping("")
    public String list(Model model, HttpServletRequest httpServletRequest) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("api", "http://localhost:8087");
        model.addAllAttributes(dataMap);
        return "pages/keyword/list";
    }


    @GetMapping("/edit")
    public String edit(Model model, HttpServletRequest httpServletRequest) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("api", "http://localhost:8087");
        model.addAllAttributes(dataMap);
        return "pages/keyword/edit";
    }

    @GetMapping("/regist")
    public String regist(Model model, HttpServletRequest httpServletRequest) {
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("api", "http://localhost:8087");
        model.addAllAttributes(dataMap);
        return "pages/keyword/regist";
    }
}
