package com.saha.amit.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Component
public class JwtTest {

    private final JwtUtil jwtUtil;

    public JwtTest(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Test 1: Generate a token without age
     */
    public void testGenerateTokenWithoutAge() {
        String username = "john.doe";
        List<String> authorities = Arrays.asList("ROLE_USER", "READ_PRIVILEGE");

        System.out.println("\n----- Test 1: Generate Token Without Age -----");
        String token = jwtUtil.generateToken(username, authorities);
        System.out.println("Generated Token: " + token);
    }

    /**
     * Test 2: Generate a token with age
     */
    public void testGenerateTokenWithAge() {
        String username = "john.doe";
        List<String> authorities = Arrays.asList("ROLE_USER", "READ_PRIVILEGE");
        Integer age = 30;

        System.out.println("\n----- Test 2: Generate Token With Age -----");
        String token = jwtUtil.generateToken(username, authorities, age);
        System.out.println("Generated Token: " + token);
    }

    /**
     * Test 3: Validate a token
     */
    public void testValidateToken() {
        String username = "john.doe";
        List<String> authorities = Arrays.asList("ROLE_USER", "READ_PRIVILEGE");
        Integer age = 30;
        String token = jwtUtil.generateToken(username, authorities, age);

        System.out.println("\n----- Test 3: Validate Token -----");
        boolean isValid = jwtUtil.validateToken(token);
        System.out.println("Is Token Valid? " + isValid);
    }

    /**
     * Test 4: Extract information from token
     */
    public void testExtractInformationFromToken() {
        String username = "john.doe";
        List<String> authorities = Arrays.asList("ROLE_USER", "READ_PRIVILEGE");
        Integer age = 30;
        String token = jwtUtil.generateToken(username, authorities, age);

        System.out.println("\n----- Test 4: Extract Information From Token -----");
        String extractedUsername = jwtUtil.getUsernameFromToken(token);
        List<String> extractedAuthorities = jwtUtil.getAuthoritiesFromToken(token);
        Integer extractedAge = jwtUtil.getAgeFromToken(token);
        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);

        System.out.println("Username: " + extractedUsername);
        System.out.println("Authorities: " + extractedAuthorities);
        System.out.println("Age: " + extractedAge);
        System.out.println("Expiration Date: " + expirationDate);
    }

    /**
     * Test 5: Check if token should be refreshed
     */
    public void testShouldRefreshToken() {
        String username = "john.doe";
        List<String> authorities = Arrays.asList("ROLE_USER", "READ_PRIVILEGE");
        Integer age = 30;
        String token = jwtUtil.generateToken(username, authorities, age);

        System.out.println("\n----- Test 5: Check If Token Should Be Refreshed -----");
        boolean shouldRefresh = jwtUtil.shouldRefreshToken(token);
        System.out.println("Should Refresh Token? " + shouldRefresh);
    }

    /**
     * Test 6: Refresh token
     */
    public void testRefreshToken() {
        String username = "john.doe";
        List<String> authorities = Arrays.asList("ROLE_USER", "READ_PRIVILEGE");
        Integer age = 30;
        String token = jwtUtil.generateToken(username, authorities, age);

        System.out.println("\n----- Test 6: Refresh Token -----");
        String refreshedToken = jwtUtil.refreshToken(token);
        System.out.println("Refreshed Token: " + refreshedToken);
    }

    /**
     * Test 7: Validate an invalid token
     */
    public void testValidateInvalidToken() {
        String username = "john.doe";
        List<String> authorities = Arrays.asList("ROLE_USER", "READ_PRIVILEGE");
        Integer age = 30;
        String token = jwtUtil.generateToken(username, authorities, age);

        System.out.println("\n----- Test 7: Validate Invalid Token -----");
        String invalidToken = token + "corrupted";
        boolean isInvalidTokenValid = jwtUtil.validateToken(invalidToken);
        System.out.println("Is Invalid Token Valid? " + isInvalidTokenValid);
    }
}
