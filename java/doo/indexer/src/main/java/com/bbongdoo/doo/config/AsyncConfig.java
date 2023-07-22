package com.bbongdoo.doo.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync(proxyTargetClass = true)
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    @Bean(name = "executor")
    public Executor threadPoolItemTaskExecutor() {
        return getExecutor();
    }

    private Executor getExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4); //기본 쓰레드 사이즈
        taskExecutor.setMaxPoolSize(4); //최대 쓰레드 사이즈
        taskExecutor.setQueueCapacity(5); //Max쓰레드가 동작하는 경우 대기하는 queue 사이즈
        taskExecutor.setThreadNamePrefix("executor_");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true); //Queue 에 남아있는 작업이완료 될 때까지 기다림
        taskExecutor.setAwaitTerminationSeconds(60); //shutdown 최대 60초 대기
        taskExecutor.initialize();

        return taskExecutor;
    }
}
