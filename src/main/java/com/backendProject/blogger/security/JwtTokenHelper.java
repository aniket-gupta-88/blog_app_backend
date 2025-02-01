package com.backendProject.blogger.security;

import com.backendProject.blogger.exceptions.ApiException; // Import your ApiException
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException; // Import ExpiredJwtException
import io.jsonwebtoken.JwtException; // Import JwtException for general JWT errors
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;
    private static final String SECRET_KEY = "JWT_SECRET_TOKEN_FOR_SECURE_LOGGING";

    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getSubject);
        } catch (JwtException e) { // Catch JWT-related exceptions
            throw new ApiException("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            return getClaimFromToken(token, Claims::getExpiration);
        } catch (JwtException e) {
            throw new ApiException("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        } catch (JwtException e) {
            throw new ApiException("Invalid token", HttpStatus.BAD_REQUEST);
        }
    }


    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new ApiException("Token has expired", HttpStatus.UNAUTHORIZED); // Specific exception for expired tokens
        } catch (JwtException e) {
            throw new ApiException("Invalid token", HttpStatus.BAD_REQUEST); // General JWT exception
        }
    }

    private Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ApiException e) { // Catch the ApiException thrown by getExpirationDateFromToken
            return true; // Treat as expired to be consistent
        }
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()
                .claims().add(claims)
                .and()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ApiException e) {
            return false;
        }
    }
}