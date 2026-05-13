package com.doo.aqqle.portal.service;

import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.LocationRequest;
import com.doo.aqqle.model.request.ShopRequest;
import com.doo.aqqle.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class PortalService {

    private final ResponseService responseService;
    private final ApplicationEventPublisher applicationEventPublisher;
    public CommonResult portalInfos (ShopRequest shopRequest, LocationRequest locationRequest) {

        applicationEventPublisher.publishEvent(shopRequest);
        applicationEventPublisher.publishEvent(locationRequest);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("LOCATION", null);
        resultMap.put("ITEM", null);

        return responseService.getSingleResult(resultMap);

    }

}




