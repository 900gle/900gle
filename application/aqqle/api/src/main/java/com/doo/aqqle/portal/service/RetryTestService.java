package com.doo.aqqle.portal.service;


import com.doo.aqqle.utils.HttpConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetryTestService {

    private final RetryService retryService;

    public void start() {
        try {
            String url = "https://www.tmon.co.kr/api/direct/v1/categorylistapi/api/strategy/filter/56040000/deals?strategyFilterNo=&localCategoryNo=&size=100&page=0&sortType=LOW_PRICE&features=&userIp=10.11.10.36&useHeader=IP&platform=PC_WEB&isPhotoReview=false";
            retryService.callApi(url);
        } catch (Exception e) {
            e.getStackTrace();
        }


        System.out.println("category api 호출 합니다.");

    }
}
