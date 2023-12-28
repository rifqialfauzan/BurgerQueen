package com.zangesterra.burgerQueen.util;

import com.zangesterra.burgerQueen.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts.SIG;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    SecretKey key = Jwts.SIG.HS256.key().build(); //or HS384.key() or HS512.key()
    private final String TOKEN_PREFIX = "Bearer ";
    private final String HEADER = "Authorization";


    public Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token).getPayload();
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis((5))))
                .signWith(key, SIG.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims = parseToken(token);

        return claims.getSubject();
    }


    public boolean verifyToken(String token) {
        Claims claims = parseToken(token);

        return claims.getExpiration().after(new Date());
    }


}
