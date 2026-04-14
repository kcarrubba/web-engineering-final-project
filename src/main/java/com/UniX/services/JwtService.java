package com.UniX.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private final Map<String, String> refreshTokens = new ConcurrentHashMap<>();

    public String generateAccessToken(String stdNo) {
        return generateToken(stdNo, accessTokenExpiration, "access");
    }

    public String generateRefreshToken(String stdNo) {
        String refreshToken = generateToken(stdNo, refreshTokenExpiration, "refresh");
        refreshTokens.put(refreshToken, stdNo);
        return refreshToken;
    }

    private String generateToken(String stdNo, long expiration, String type) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(stdNo)
                .claim("type", type)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String validateAccessToken(String token) {
        try {
            // Throws exception if not valid
            Claims claims = Jwts.parser()
                 .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
            
            // Check whether token is expired
            if (claims.getExpiration().after(new Date()))
                return claims.getSubject();
            else    
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String validateRefreshToken(String token) {
        try {
            // Throws exception if not valid
            if (!refreshTokens.containsKey(token)) return null;
            
            Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

            // Check whether token is expired
            if (claims.getExpiration().after(new Date()))
                return claims.getSubject();
            else    
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    public void invalidateRefreshToken(String token) {
        refreshTokens.remove(token);
    }

}