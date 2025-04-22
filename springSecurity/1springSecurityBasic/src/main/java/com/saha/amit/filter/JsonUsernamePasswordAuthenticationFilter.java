package com.saha.amit.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saha.amit.dto.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.io.IOException;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/v1/api/login"); // your custom login endpoint
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        if (!MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            throw new AuthenticationServiceException("Unsupported Content Type: " + request.getContentType());
        }

        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            return this.getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to parse login request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Set SecurityContext and persist in session
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );
        // --- Add cookies ---
        // Secure, HttpOnly cookie (server-only)
        Cookie secureCookie = new Cookie("SECURE_TOKEN", "secure-value-12345");
        secureCookie.setHttpOnly(true);        // JS can't access
        secureCookie.setSecure(true);          // Only sent over HTTPS
        secureCookie.setPath("/");             // Available to entire app
        secureCookie.setMaxAge(3600);          // 1 hour
        response.addCookie(secureCookie);

        // Plain, JS-readable cookie
        Cookie plainCookie = new Cookie("PLAIN_SESSION_ID", "visible-value-abcde");
        plainCookie.setHttpOnly(false);        // JS can access
        plainCookie.setSecure(false);          // OK for HTTP (dev env)
        plainCookie.setPath("/");
        plainCookie.setMaxAge(3600);
        response.addCookie(plainCookie);

        // Let the success handler respond (configured in SecurityConfig)
        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }




    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // Let the failure handler respond (configured in SecurityConfig)
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }

}
