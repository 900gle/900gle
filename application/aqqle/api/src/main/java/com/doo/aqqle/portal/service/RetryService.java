package com.doo.aqqle.portal.service;


import com.doo.aqqle.utils.HttpConnection;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@EnableRetry
public class RetryService {


    /**
     * ✅ 실패 시 최대 3번 재시도, 각 재시도 간 2초 대기
     */
    @Retryable(
            value = { Exception.class },   // 재시도할 예외 지정
            maxAttempts = 3,                      // 최대 재시도 횟수
            backoff = @Backoff(delay = 2000))     // 재시도 간 대기 시간 (ms)
    public String callApi(String url) throws Exception{

        System.out.println("🌐 API 호출 시도: " + url);

        // ❗ HttpConnection 호출 시 예외 발생 시 재시도 유도
       String response = HttpConnection.sendGet(url, "UTF-8", 15000, 30000);

        System.out.println(response);

        System.out.println("✅ API 호출 성공: " + url);
        return "✅ 호출 성공 - URL: " + url;

    }

    /**
     * ✅ 모든 재시도 실패 시 호출되는 복구 메서드
     */
    @Recover
    public String recover(Exception e, String url) {
        System.err.println("🛠 재시도 후 실패 - 대체 응답 반환");
        return "⚠️ 대체 응답: 서비스가 일시적으로 중단되었습니다.";
    }
}
