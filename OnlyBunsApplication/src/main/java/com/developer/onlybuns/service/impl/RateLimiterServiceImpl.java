package com.developer.onlybuns.service.impl;
import com.developer.onlybuns.service.RateLimiterService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterServiceImpl implements RateLimiterService {

    private final ConcurrentHashMap<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();
    private final RateLimiterRegistry rateLimiterRegistry;

    public RateLimiterServiceImpl() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(3) // 3 requests for quicker testing, later update to 5
                .limitRefreshPeriod(Duration.ofMinutes(3)) // per 1 minute
                .timeoutDuration(Duration.ofMillis(0))
                .build();

        this.rateLimiterRegistry = RateLimiterRegistry.of(config);
    }

    private boolean isRateLimited(String key) {
        RateLimiter rateLimiter = rateLimiterMap.computeIfAbsent(key,
                k -> rateLimiterRegistry.rateLimiter(k));
        return !rateLimiter.acquirePermission();
    }

    @Override
    public boolean isLoginRateLimited(String ipAddress) {
        return isRateLimited("LOGIN_" + ipAddress);
    }

    @Override
    public boolean isPostCreationRateLimited(Integer userId) {
        return isRateLimited("POST_" + userId);
    }
}
