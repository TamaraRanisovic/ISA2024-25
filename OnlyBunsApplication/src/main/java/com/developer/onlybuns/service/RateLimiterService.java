package com.developer.onlybuns.service;

import io.github.resilience4j.ratelimiter.RateLimiter;

import java.util.Map;

public interface RateLimiterService {
    boolean isLoginRateLimited(String ipAddress);
    boolean isPostCreationRateLimited(Integer userId);
}
