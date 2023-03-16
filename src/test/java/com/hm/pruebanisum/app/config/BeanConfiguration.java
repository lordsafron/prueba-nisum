package com.hm.pruebanisum.app.config;

import com.hm.pruebanisum.app.util.JWTTokenManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BeanConfiguration {

    @Bean
    public JWTTokenManager jwtTokenManager() {
        return new JWTTokenManager(new JwtBeanConfig());
    }

}
