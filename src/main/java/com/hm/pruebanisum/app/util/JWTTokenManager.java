package com.hm.pruebanisum.app.util;

import com.hm.pruebanisum.app.config.JwtBeanConfig;
import com.hm.pruebanisum.app.exceptions.TokenDecodeException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTTokenManager {

    private final JwtBeanConfig config;

    public String createJWT(String id, String issuer, String subject) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(config.getSecretKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (Long.parseLong(config.getExpiryTime()) >= 0) {
            Date exp = addSeconds(now, Integer.parseInt(config.getExpiryTime()));
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public Claims decodeJWT(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(config.getSecretKey()))
                .parseClaimsJws(jwt).getBody();

        } catch (ExpiredJwtException ex) {
            throw new TokenDecodeException("token expirado");
        } catch (Exception ex) {
            throw new TokenDecodeException("token no válido");
        }
        return claims;
    }

    public Date addSeconds(Date date, Integer seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

}
