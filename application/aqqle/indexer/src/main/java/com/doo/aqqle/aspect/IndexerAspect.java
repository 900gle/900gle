package com.doo.aqqle.aspect;

import com.doo.aqqle.service.IndexerLoggerService;
import com.doo.aqqle.vo.IndexerLogVO;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class IndexerAspect {
    private final IndexerLoggerService indexerLoggerService;
    @AfterReturning(pointcut = "@annotation(com.doo.aqqle.annotation.IndexerLog)", returning = "response")
    public void IndexLogAfter(JoinPoint joinPoint, Object response) {
        Object[] args = joinPoint.getArgs();
        String type = (String) args[0];
        IndexerLogVO indexerLogVO = new IndexerLogVO();
        indexerLogVO.setType(type);
        indexerLoggerService.saveLog(indexerLogVO);
    }
}

