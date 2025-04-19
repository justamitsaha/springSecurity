package com.saha.amit.filter;

import com.saha.amit.dto.CustomUser;
import com.saha.amit.service.CustomUserDetailsService;
import com.saha.amit.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class DataBaseAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final Log log = LogFactory.getLog(DataBaseAuthorizationFilter.class);

    public DataBaseAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ Skip filtering if user is already authenticated
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            log.info("User already authenticated, skipping DB auth filter.");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("DataBaseAuthorizationFilter starting");

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            try {
                if (!jwtUtil.validateToken(jwt)) {
                    throw new JwtException("Invalid or expired token.");
                }

                String username = jwtUtil.getUsernameFromToken(jwt);
                List<String> roles = jwtUtil.getAuthoritiesFromToken(jwt);
                Integer age = jwtUtil.getAgeFromToken(jwt); // Add this method below ⬇️

                if (request.getRequestURI().startsWith("/v3/api/") && (age == null || age < 18)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"error\":\"Access denied: must be 18+ to access this route. DataBaseAuthorizationFilter\"}");
                    return;
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        roles.stream().map(SimpleGrantedAuthority::new).toList()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

                if ("true".equals(System.getenv("DEBUG_JWT"))) {
                    response.setHeader("X-User", username);
                    response.setHeader("X-Roles", String.join(",", roles));
                    if (age != null) response.setHeader("X-Age", String.valueOf(age));
                }

                // Optional: auto-refresh logic here (could also move into JwtUtil)
                if (jwtUtil.shouldRefreshToken(jwt)) {
                    String refreshed = jwtUtil.refreshToken(jwt);
                    response.setHeader("X-Refreshed-Token", refreshed);
                }

            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"Invalid or expired token. DataBaseAuthorizationFilter\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/v1/api/login")
                || request.getRequestURI().startsWith("/v2/api/login")
                || request.getRequestURI().startsWith("/v3/api/login")
                || request.getRequestURI().startsWith("/public/");
    }
}


