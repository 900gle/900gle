package com.doo.aqqle.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimerAop {

    @Around("@annotation(com.doo.aqqle.annotation.Timer)")
    public Object timeMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();  // 메서드 실행

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        log.info("메서드 {} 실행 시간: {} ms", joinPoint.getSignature(), duration);

        return result;
    }
}
