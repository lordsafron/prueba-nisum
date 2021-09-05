package com.hm.pruebanisum.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtBeanConfig {

    private String secretKey;
    private String expiryTime;
}
