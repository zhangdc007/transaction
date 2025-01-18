package com.mybank.management.transaction.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangdaochuan
 * @time 2025/1/18 03:04
 */
public class CacheManager {

    // 创建缓存实例
    private static final Cache<String, Object> cache = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(500)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .recordStats()
            .build();

    /**
     * 将键值对放入缓存
     *
     * @param key   缓存键
     * @param value 缓存值
     */
    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * 从缓存中获取值
     *
     * @param key 缓存键
     * @return 缓存值，如果不存在则返回null
     */
    public static Object get(String key) {
        return cache.getIfPresent(key);
    }

    public static void remove(String key) {
        cache.invalidate(key);
    }
}