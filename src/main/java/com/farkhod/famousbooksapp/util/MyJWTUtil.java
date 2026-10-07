package com.farkhod.famousbooksapp.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.util.Date;

@UtilityClass
public class MyJWTUtil {
    public static String generateToken(String subject, long expiry, String secretKeyValue) {
        return Jwts
                .builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiry * 1000))
                .signWith(secretKey(secretKeyValue))
                .compact();
    }

    public static SecretKey secretKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public static String getSubjectFromToken(String token, String secretKeyValue) {
        return Jwts.parser()
                .verifyWith(secretKey(secretKeyValue))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
