package com.hm.pruebanisum.app.util;

import com.hm.pruebanisum.app.config.JwtBeanConfig;
import com.hm.pruebanisum.app.constants.GeneralConstants;
import com.hm.pruebanisum.app.exceptions.TokenDecodeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTTokenManager {

    private final JwtBeanConfig config;

    public String createJWT(String id, String issuer, String subject) {

        var signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        var now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(config.getSecretKey());
        var signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        var builder = Jwts.builder().setId(id)
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
        Claims claims;
        try {
            claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(config.getSecretKey()))
                .parseClaimsJws(jwt.split(GeneralConstants.EMPTY)[GeneralConstants.ONE]).getBody();

        } catch (ExpiredJwtException ex) {
            throw new TokenDecodeException("token expirado");
        } catch (Exception ex) {
            throw new TokenDecodeException("token no válido");
        }
        return claims;
    }

    public Date addSeconds(Date date, Integer seconds) {
        var cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

}
