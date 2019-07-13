package com.shani.message.processor.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    public static final String STATUS_CACHE = "statusCache";

    private final int statusMaxEntries;
    private final long statusTtlMin;

    public CacheConfig(@Value("${cache.statusTtlMin}") int statusTtlMin,
                       @Value("${cache.statusMaxEntries}") int statusMaxEntries) {
        this.statusTtlMin = statusTtlMin;
        this.statusMaxEntries = statusMaxEntries;
    }

    @Bean
    public CacheManager cacheManager() {
        return new CompositeCacheManager(
                new ConcurrentMapCacheManager(STATUS_CACHE) {
                    @Override
                    protected Cache createConcurrentMapCache(final String name) {
                        return new ConcurrentMapCache(name,
                                CacheBuilder.newBuilder()
                                        .expireAfterWrite(statusTtlMin, TimeUnit.MINUTES)
                                        .maximumSize(statusMaxEntries)
                                        .build().asMap(), false);
                    }
                }
        );
    }

}
