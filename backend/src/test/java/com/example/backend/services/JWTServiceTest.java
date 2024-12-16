package com.example.backend.services;

import com.example.backend.dtos.AuthUserInfo;
import com.example.backend.entities.User;
import com.example.backend.entities.UserDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;


class JWTServiceTest {

    private JWTService jwtService;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JWTService();
        jwtService.setEXPIRATION_DAYS(7L);
        jwtService.setSECRET_KEY("testSecret1234567890987654321234567890876543223456789987654322345678998765432342345678998765432345678");
    }

    private AuthUserInfo testAuthUserInfo(long id, boolean isAdmin){
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setUserId(id);
        authUserInfo.setIsAdmin(isAdmin);
        return authUserInfo;
    }

    @Test
    void generateCorrectToken() {
        String token = jwtService.generateToken(testAuthUserInfo(1, false));
        assertNotNull(token);
    }

    @Test
    void generateCorrectTokenAdmin() {
        String token = jwtService.generateToken(testAuthUserInfo(1, true));
        assertNotNull(token);
    }

    @Test
    void generateToken_InvalidSecretKey() {
        jwtService.setSECRET_KEY("testSecret");
        assertThrows(Exception.class, () -> jwtService.generateToken(testAuthUserInfo(1, false)));
    }

    @Test
    void isTokenValid_ReturnsTrue() {
        String token = jwtService.generateToken(testAuthUserInfo(1, false));
        User user = new User();
        user.setId(1);
        userDetails = new UserDetail(user, null);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValid_ReturnsFalse() {
        String token = jwtService.generateToken(testAuthUserInfo(1, false));
        User user = new User();
        user.setId(2);
        userDetails = new UserDetail(user, null);
        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

//    @Test
//    void isTokenValid_ReturnsTrue() {
//        String token = jwtService.generateToken(testAuthUserInfo(1, false));
//        User user = new User();
//        user.setId(1);
//        userDetails = new UserDetail(user, null);
//        boolean result = jwtService.isTokenValid(token, userDetails);
//        assertTrue(result);
//    }

    @Test
    void isTokenValid_Expired() {
        jwtService.setEXPIRATION_DAYS(0L);
        String token = jwtService.generateToken(testAuthUserInfo(1, false));
        User user = new User();
        user.setId(1);
        userDetails = new UserDetail(user, null);
        assertThrows(Exception.class, () -> jwtService.isTokenValid(token, userDetails));
    }



    @Test
    void isTokenExpired() {
        String token = jwtService.generateToken(testAuthUserInfo(1, false));
//        assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    void extractUserId() {
        String token = jwtService.generateToken(testAuthUserInfo(1, false));
        assertEquals(1, jwtService.extractUserId(token));
    }

    @Test
    void extractUserId_ThrowsException() {
        assertThrows(Exception.class, () -> jwtService.extractUserId("empty"));
    }

    @Test
    void extractClaim() {
        String token = jwtService.generateToken(testAuthUserInfo(1, false));
        assertNotNull(jwtService.extractClaim(token, claims -> Long.parseLong(claims.getSubject())));
    }
}