//package com.example.backend.services;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////@SpringBootTest
//class JWTBlacklistServiceTest {
//
//
//
//    @Mock
//    private StringRedisTemplate redisTemplate;
//
//
//    @InjectMocks
//    private JWTBlacklistService jwtBlacklistService;
//
////    @Override
////    public void afterPropertiesSet() throws Exception {
//////        this.jwtBlacklistService = new JWTBlacklistService();
//////        this.redisTemplate = new StringRedisTemplate();
//////        jwtBlacklistService.setRedisTemplate(redisTemplate);
////        MockitoAnnotations.openMocks(this);
////        jwtBlacklistService.setEXPIRATION_DAYS(7L);
////    }
//    @BeforeEach
//    void setUp() {
//        jwtBlacklistService = new JWTBlacklistService();
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
//        connectionFactory.setHostName("localhost");
//        connectionFactory.setPort(6379);
//        connectionFactory.afterPropertiesSet();
//
//        redisTemplate = new StringRedisTemplate(connectionFactory);
//        jwtBlacklistService.setRedisTemplate(redisTemplate);
//        jwtBlacklistService.setEXPIRATION_DAYS(7L);
//    }
//
//    @Test
//    void testBlacklistToken() {
//        assertDoesNotThrow(() -> jwtBlacklistService.BlacklistToken("token"));
//    }
//
////    @Test
////    void testIsTokenBlacklisted() {
////        assertFalse(jwtBlacklistService.isTokenBlacklisted("token"));
////    }
//
//}