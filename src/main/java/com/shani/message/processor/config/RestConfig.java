package com.shani.message.processor.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class RestConfig implements AsyncConfigurer {

    private static final int INITIAL_THREAD_POOL_SIZE = 20;
    private static final int MAX_THREAD_POOL_SIZE = 200;
    private static final int MAX_THREAD_POOL_QUEUE_CAPACITY = 1000;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(INITIAL_THREAD_POOL_SIZE);
        executor.setMaxPoolSize(MAX_THREAD_POOL_SIZE);
        executor.setQueueCapacity(MAX_THREAD_POOL_QUEUE_CAPACITY);
        executor.setThreadNamePrefix("ProcessorExecutor-");
        executor.initialize();
        return executor;
    }

    @Autowired
    private AsyncExceptionHandler asyncExceptionHandler;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }
}
