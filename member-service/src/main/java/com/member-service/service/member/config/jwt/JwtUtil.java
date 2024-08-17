package com.mod1.service.member.config.jwt;

import com.mod1.service.member.config.property.JwtProperty;
import com.mod1.service.member.exception.Unauthorized;
import com.mod1.service.member.config.property.JwtProperty;
import com.mod1.service.member.exception.Unauthorized;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final JwtProperty jwtProperty;

    public String generateJws(JwtBuilder jwtBuilder, Long maxAgeInMinutes) {
        return jwtBuilder
                .expiration(Date.from(Instant.now().plus(maxAgeInMinutes, ChronoUnit.MINUTES)))
                .signWith(Keys.hmacShaKeyFor(jwtProperty.getKey()))
                .compact();
    }

    public Jws<Claims> validateAndParseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtProperty.getKey()))
                    .build()
                    .parseSignedClaims(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token: {}", e.getMessage());
            throw new Unauthorized("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token: {}", e.getMessage());
            throw new Unauthorized("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty: {}", e.getMessage());
            throw new Unauthorized("Empty JWT claims");
        }
    }
}
