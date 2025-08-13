package com.saha.amit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saha.amit.filter.*;
import com.saha.amit.service.CustomUserDetailsService;
import com.saha.amit.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Configuration
public class SecurityConfig {


    private final CorsConfigurationSource corsConfigurationSource;
    private final Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer;
    private final Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizationRules;
    private final Customizer<FormLoginConfigurer<HttpSecurity>> formLoginConfigurerCustomizer;




    @Lazy
    @Autowired
    public SecurityConfig(CorsConfigurationSource corsConfigurationSource,
                          Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer,
                          Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizationRules, Customizer<FormLoginConfigurer<HttpSecurity>> formLoginConfigurerCustomizer) {
        this.corsConfigurationSource = corsConfigurationSource;
        this.csrfCustomizer = csrfCustomizer;
        this.authorizationRules = authorizationRules;
        this.formLoginConfigurerCustomizer = formLoginConfigurerCustomizer;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            @Qualifier("defaultAuthenticationManager") AuthenticationManager defaultAuthManager,
                                            @Qualifier("dbAuthManager") AuthenticationManager dbAuthManager,
                                            JwtUtil jwtUtil) throws Exception {

        //CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        JsonUsernamePasswordAuthenticationFilter jsonAuthFilter = new JsonUsernamePasswordAuthenticationFilter(defaultAuthManager);
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(defaultAuthManager, jwtUtil);
        DataBaseLoginFilter dbLoginFilter = new DataBaseLoginFilter(dbAuthManager, jwtUtil);
        DataBaseAuthorizationFilter dbAuthorizationFilter = new DataBaseAuthorizationFilter(jwtUtil);

        jsonAuthFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            if (isCorsRequest(request)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Login successful JsonUsernamePasswordAuthenticationFilter \"}");
            } else {
                //For CORS
                setCORSHeader(request, response);
            }
        });

        jsonAuthFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Login failed JsonUsernamePasswordAuthenticationFilter\"}");
        });

        http
                .authorizeHttpRequests(authorizationRules)
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource))
                .csrf(csrfCustomizer)
                //.csrf(csrf -> csrf.disable())          // ✅ for H2 POSTs and for CORS requests
                .formLogin(formLoginConfigurerCustomizer)
                //.requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) // Only HTTPS
                .authenticationManager(defaultAuthManager) // ✅ Forces form login to use in-memory auth only
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // ✅ for H2 frames
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(jsonAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(dbLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(dbAuthorizationFilter, JwtAuthorizationFilter.class)
                .httpBasic(Customizer.withDefaults());     //credentials inside the httpRequest header by Base64 encoding them, BasicAuthenticationFilter
        return http.build();
    }


    public boolean isCorsRequest(HttpServletRequest request) {
        String origin = request.getHeader("Origin");

        // If there's no Origin header, it's not a CORS request
        if (origin == null) {
            return false;
        }

        // Get the current request URL
        String requestUrl = request.getRequestURL().toString();
        String requestHost = URI.create(requestUrl).getHost();

        // Get the origin host
        String originHost = URI.create(origin).getHost();

        // If they differ, it's a CORS request
        return !originHost.equals(requestHost);
    }

    private void setCORSHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the current session ID
        HttpSession session = request.getSession();
        String sessionId = session.getId();

        // Set response parameters
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        // Create JSON response with session ID
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message", "Login successful JsonUsernamePasswordAuthenticationFilter");
        responseMap.put("sessionId", sessionId);

        // Use Jackson or Gson to convert map to JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(responseMap);

        response.getWriter().write(jsonResponse);
    }

//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

}
