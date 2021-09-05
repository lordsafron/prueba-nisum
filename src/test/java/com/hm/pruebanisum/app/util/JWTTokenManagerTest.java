package com.hm.pruebanisum.app.util;

import com.hm.pruebanisum.app.config.JwtBeanConfig;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTTokenManagerTest {

    JwtBeanConfig config;
    JWTTokenManager jwtTokenManager;

    @BeforeEach
    void setUp() {
        config = mock(JwtBeanConfig.class);
        jwtTokenManager = new JWTTokenManager(config);
    }

    @Test
    void testCreateJWT() {
        when(config.getSecretKey()).thenReturn("palabra-secreta");
        when(config.getExpiryTime()).thenReturn("3600");
        String id = UUID.randomUUID().toString();
        String issuer = "email@dominio.cl";
        String subject = "algo";

        String jwt = jwtTokenManager.createJWT(id, issuer, subject);

        assertTrue(jwt.length() > 0);
        verify(config, atLeastOnce()).getSecretKey();
        verify(config, atLeastOnce()).getExpiryTime();
    }

    @Test
    void testDecodeJWT() {
        when(config.getSecretKey()).thenReturn("palabra-secreta");
        when(config.getExpiryTime()).thenReturn("3600");
        String id = UUID.randomUUID().toString();
        String issuer = "email@dominio.cl";
        String subject = "algo";

        String jwt = jwtTokenManager.createJWT(id, issuer, subject);

        assertTrue(jwt.length() > 0);


        Claims claims = jwtTokenManager.decodeJWT(jwt);

        assertEquals("email@dominio.cl", claims.get("iss"));

        verify(config, times(2)).getSecretKey();
        verify(config, atLeastOnce()).getExpiryTime();

    }

    @Test
    void testAddSeconds() {
        int timeExp= 3600;
        Date now = new Date();

        Date dateExp = jwtTokenManager.addSeconds(now, timeExp);

        assertFalse(dateExp.equals(now));
        assertTrue(dateExp.getTime() > now.getTime());

    }


}