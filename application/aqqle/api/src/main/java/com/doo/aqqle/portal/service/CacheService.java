package com.doo.aqqle.portal.service;


import com.doo.aqqle.component.CacheCompo;
import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheCompo cacheCompo;
    private final ResponseService responseService;
    public CommonResult getCaches(String countryCode) {
        List<String> responseList = new ArrayList<>();
        try {
            responseList.add(cacheCompo.getCacheName(countryCode).getName());
            responseList.add(cacheCompo.getCacheFilter(countryCode).getName());
//            responseList.add(cacheCompo.getCacheAttr(countryCode).getName());
//            responseList.add(cacheCompo.getCacheAggs(countryCode).getName());
//            responseList.add(cacheCompo.getCacheAvg(countryCode).getName());
//            responseList.add(cacheCompo.getCacheSum(countryCode).getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseService.getListResult(responseList);
    }
}
