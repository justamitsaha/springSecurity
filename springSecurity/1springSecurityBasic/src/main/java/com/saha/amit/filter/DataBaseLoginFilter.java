package com.saha.amit.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saha.amit.dto.CustomUser;
import com.saha.amit.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DataBaseLoginFilter extends OncePerRequestFilter {

    private final Log log = LogFactory.getLog(DataBaseLoginFilter.class.getName());

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public DataBaseLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Parse username/password from JSON
            Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            log.info("Attempting login for user: " + username);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authResult = authenticationManager.authenticate(authentication);

            // Extract custom user details
            UserDetails principal = (UserDetails) authResult.getPrincipal();
            int age = 0;

            if (principal instanceof CustomUser customUser) {
                age = customUser.getAge();
                if (age < 18) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"error\":\"User must be at least 18 years old DataBaseLoginFilter\"}");
                    return;
                }
            }

            // Create JWT
            List<String> authorities = principal.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toList());

            String jwt = jwtUtil.generateToken(username, authorities, age);

            // Send token in header
            response.setHeader("Authorization", "Bearer " + jwt);
            log.info("JWT issued for user: " + username);

        } catch (AuthenticationException e) {
            System.out.println("Authentication failed: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\":\"Authentication failed DataBaseLoginFilter:\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/v3/api/login");
    }
}
