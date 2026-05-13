package com.doo.aqqle.portal.service;


import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.LikeRequest;
import com.doo.aqqle.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneralCacheService {


    private final RedisService redisService;

    private Long like;

    private final ResponseService responseService;

    public CommonResult likeCache(LikeRequest request) {
        if ("I".equals(request.getAction())) {
            like = redisService.incrementCache(request.getProductNo());
        } else {
            like = redisService.decrementCache(request.getProductNo());
        }
        return responseService.getSingleResult(like);
    }
}
