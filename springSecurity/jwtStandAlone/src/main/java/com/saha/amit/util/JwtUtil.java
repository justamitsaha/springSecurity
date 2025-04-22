package com.saha.amit.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {


    /*make sure it's sufficiently long (at least 256 bits / 32 bytes) for HS256
    below key is  32 characters long.
    Since each character in a standard string typically uses 8 bits (1 byte) of storage, your key is 32 bytes or 256 bits
    For enterprise distributed systems, there are several secure approaches to inject secrets without hardcoding them:
    1. Secrets Management Services

    HashiCorp Vault: A dedicated secrets management tool that provides centralized storage, dynamic secrets generation, encryption, and access control
    AWS Secrets Manager/Azure Key Vault/Google Secret Manager: Cloud-native services for storing and rotating secrets
    CyberArk: Enterprise password vault with robust access controls

    2. Configuration Management

    Kubernetes Secrets: Store secrets in Kubernetes and inject them as environment variables or mounted volumes
    Environment Variables: Load secrets from environment variables that are set during deployment
    Spring Cloud Config Server/Consul: Use dedicated configuration services with encryption capabilities
    */
    private final String secret = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";


    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // with below option the key will be different each time your application restarts, invalidating all previously issued tokens
        //this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        //this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = generateSecretKeyFromString(secret);

    }

    private SecretKey generateSecretKeyFromString(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username, List<String> authorities) {
        return Jwts.builder()
                .setIssuer("AmitSaha")
                .setSubject("JWT Token")
                .claim("username", username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Overloaded method with optional age
    public String generateToken(String username, List<String> authorities, Integer age) {
        JwtBuilder builder = Jwts.builder()
                .setIssuer("AmitSaha")
                .setSubject("JWT Token")
                .claim("username", username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(secretKey, SignatureAlgorithm.HS256);

        if (age != null) {
            builder.claim("age", age);
        }

        return builder.compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Log the exception if needed
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }

    public List<String> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("authorities", List.class);
    }


    //For DataBaseAuthorizationFilter

    public Integer getAgeFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("age", Integer.class);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean shouldRefreshToken(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && (expiration.getTime() - System.currentTimeMillis()) < 5 * 60 * 1000;
    }

    public String refreshToken(String oldToken) {
        String username = getUsernameFromToken(oldToken);
        List<String> roles = getAuthoritiesFromToken(oldToken);
        Integer age = getAgeFromToken(oldToken);
        return generateToken(username, roles, age);
    }




}


/*
In JWT (JSON Web Token) terminology:

A claim is a key-value pair of information about the user or the token itself that is stored inside the token.

Claims can be:

Registered claims: standardized (like iss = issuer, sub = subject, exp = expiry)

Public claims: commonly used, like username, roles, etc.

Private claims: custom, like age, department, etc.

Think of claims as:

“What does this token say about the user?”
 */
