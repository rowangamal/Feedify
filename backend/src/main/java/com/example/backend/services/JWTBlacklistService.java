package com.example.backend.services;

import com.example.backend.exceptions.JWTBlacklistedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JWTBlacklistService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${jwt.expiration.days}")
    private Long EXPIRATION_DAYS;

    public void BlacklistToken(String token) {
        if (isTokenBlacklisted(token)) {
            throw new JWTBlacklistedException("Token is already blacklisted");
        }
        redisTemplate.opsForValue().set(token, "blacklisted", EXPIRATION_DAYS, TimeUnit.DAYS);
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(token);
    }
}
