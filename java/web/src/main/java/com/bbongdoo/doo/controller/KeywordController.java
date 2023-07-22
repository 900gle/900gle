package com.bbongdoo.doo.controller;


import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("keyword")
@RequiredArgsConstructor
public class KeywordController {

//    @Value("${host.security}")
//    private String host;

//    private final UserService userService;


    @GetMapping("")
    public String roles(Model model, HttpServletRequest httpServletRequest) {

//        Map<String, Object> dataMap = new HashMap<>();
//        HttpSession session = httpServletRequest.getSession();
//
//        String url = HostConfig.getUrl("/v1/user?lang=ko");
//
//        String token = null;
//        Cookie cookies[] = httpServletRequest.getCookies();
//
//        if (cookies != null) {
//            token = Arrays.stream(cookies)
//                    .filter(f -> f.getName().equals("token"))
//                    .map(m -> m.getValue())
//                    .collect(Collectors.joining());
//        }
//        JSONObject jsonObject = UserSendRestUtil.getUserInfoByToken(url, (String) token);
//        JSONObject jsonData = (JSONObject) jsonObject.get("data");
//
//        List<String> roles = (List<String>) jsonData.get("roles");
//        List<String> auths =  (List<String>) jsonData.get("auths");
//
//        List<String> authorities =  new ArrayList<>();
//        authorities.add("lecture");
//        authorities.add("metaverse");
//
//        List<String> dataRoles =  new ArrayList<>();
//        dataRoles.add("ADMIN");
//        dataRoles.add("USER");
//        dataRoles.add("CDO");
//
//
//        dataMap.put("dataRoles", dataRoles);
//        dataMap.put("roles", roles);
//        dataMap.put("auths", auths);
//        dataMap.put("authorities", authorities);
//        dataMap.put("name", jsonData.get("name"));
//        dataMap.put("uid", jsonData.get("uid"));
//        dataMap.put("msrl", jsonData.get("msrl"));
//        dataMap.put("host", host);
//        model.addAllAttributes(dataMap);
        return "keywords";
    }



//    @GetMapping("")
//    public String roles(Model model, HttpServletRequest httpServletRequest) {
//
//        Map<String, Object> dataMap = new HashMap<>();
//        HttpSession session = httpServletRequest.getSession();
//        String url = HostConfig.getUrl("/v1/user?lang=ko");
//        String token = null;
//        Cookie cookies[] = httpServletRequest.getCookies();
//
//        if (cookies != null) {
//            token = Arrays.stream(cookies)
//                    .filter(f -> f.getName().equals("token"))
//                    .map(m -> m.getValue())
//                    .collect(Collectors.joining());
//        }
//        JSONObject jsonObject = UserSendRestUtil.getUserInfoByToken(url, (String) token);
//        JSONObject jsonData = (JSONObject) jsonObject.get("data");
//
//        List<String> roles = (List<String>) jsonData.get("roles");
//        List<String> auths =  (List<String>) jsonData.get("auths");
//        System.out.println(auths);
//
//
//        dataMap.put("roles", roles);
//        dataMap.put("auths", auths);
//        dataMap.put("name", jsonData.get("name"));
//        dataMap.put("uid", jsonData.get("uid"));
//        dataMap.put("msrl", jsonData.get("msrl"));
//        dataMap.put("host", host);
//        model.addAllAttributes(dataMap);
//        return "users";
//    }

    @GetMapping("/regist")
    public String regist(Model model, HttpServletRequest httpServletRequest) {

//        Map<String, Object> dataMap = new HashMap<>();
//        HttpSession session = httpServletRequest.getSession();
//
//        String url = HostConfig.getUrl("/v1/user?lang=ko");
//
//        String token = null;
//        Cookie cookies[] = httpServletRequest.getCookies();
//
//        if (cookies != null) {
//            token = Arrays.stream(cookies)
//                    .filter(f -> f.getName().equals("token"))
//                    .map(m -> m.getValue())
//                    .collect(Collectors.joining());
//        }
//        JSONObject jsonObject = UserSendRestUtil.getUserInfoByToken(url, (String) token);
//        JSONObject jsonData = (JSONObject) jsonObject.get("data");
//
//        List<String> roles = (List<String>) jsonData.get("roles");
//        List<String> auths =  (List<String>) jsonData.get("auths");
//
//
//        dataMap.put("dataRoles", userService.getRoles(host));
//        dataMap.put("roles", roles);
//        dataMap.put("auths", auths);
//        dataMap.put("authorities", userService.getAuths(host));
//        dataMap.put("name", jsonData.get("name"));
//        dataMap.put("uid", jsonData.get("uid"));
//        dataMap.put("msrl", jsonData.get("msrl"));
//        dataMap.put("host", host);
//        model.addAllAttributes(dataMap);
        return "keyword_regist";
    }

}
