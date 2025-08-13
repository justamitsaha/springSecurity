package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@Configuration
public class CustomAuthorizationRulesConfig {

    /**
     * Defines authorization rules for different application endpoints.
     * <p>
     * Public endpoints are accessible to everyone,
     * while some require USER or ADMIN roles.
     *
     * @return authorization rules customizer
     */
    @Bean
    public Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizationRules() {
        return auth -> auth
                .requestMatchers("/favicon.ico", "/public/style.css", "/public/main.js", "/images/**",
                        "/public/home.html", "/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/v1/api/login", "/v2/api/login", "/v3/api/login",
                        "/public/publicUpdate").permitAll()
                .requestMatchers("/private/protectedUpdate").authenticated()
                .requestMatchers("/private/balance", "/private/message").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/announcement", "/admin/loan").hasRole("ADMIN")
                .requestMatchers("/public/**", "/error").permitAll();
    }
}
