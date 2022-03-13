package com.redspider.pss.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class TokenHelper {

    private static final long TIMEOUT = 3600 * 1000 * 24;

    private static final String SECRET_KEY = "pss-backend";

    public static String generate(UserPayload payload) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setId(payload.getId().toString())
                .setSubject(payload.getName())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + TIMEOUT))
                .addClaims(buildClaims(payload))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static String generateByUserId(Long id) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setId(id.toString())
                .setSubject(id.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + TIMEOUT))
                .addClaims(Collections.singletonMap(UserPayload.USER_ID, id))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Claims resolve(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private static Map<String, Object> buildClaims(UserPayload payload) {
        return Collections.singletonMap(UserPayload.USER_ID, payload.getId());
    }

}
