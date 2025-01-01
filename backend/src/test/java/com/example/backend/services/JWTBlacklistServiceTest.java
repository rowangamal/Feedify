package com.example.backend.services;

import com.example.backend.exceptions.JWTBlacklistedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class JWTBlacklistServiceTest {


//    @Mock
    private StringRedisTemplate redisTemplate;

//    @InjectMocks
    private JWTBlacklistService jwtBlacklistService;

    @BeforeEach
    void setUp() {
        jwtBlacklistService = new JWTBlacklistService();
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.afterPropertiesSet();
//

        redisTemplate = new StringRedisTemplate(connectionFactory);
        jwtBlacklistService.setRedisTemplate(redisTemplate);
//        MockitoAnnotations.openMocks(this);
        jwtBlacklistService.setEXPIRATION_DAYS(7L);
    }

    @Test
    void testBlacklistToken() {
        assertDoesNotThrow(() -> jwtBlacklistService.BlacklistToken("token blacklisted"));
        redisTemplate.delete("token blacklisted");
    }

    @Test
    void testBlacklistTokenAlreadyBlacklisted() {
        redisTemplate.opsForValue().set("token", "blacklisted", 7, TimeUnit.DAYS);
        assertThrows(JWTBlacklistedException.class, () -> jwtBlacklistService.BlacklistToken("token"));
    }

    @Test
    void testIsTokenBlacklisted() {
        redisTemplate.opsForValue().set("token", "blacklisted", 7, TimeUnit.DAYS);
        assertTrue(jwtBlacklistService.isTokenBlacklisted("token"));
    }



}