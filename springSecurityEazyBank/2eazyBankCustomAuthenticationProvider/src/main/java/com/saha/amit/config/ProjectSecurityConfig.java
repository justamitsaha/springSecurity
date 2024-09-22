package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
    UsernamePasswordAuthenticationFilter  --> attemptAuthentication --> Extract username pwd
    AbstractUserDetailsAuthenticationProvider  --> authenticate
    DaoAuthenticationProvider  -->createSuccessAuthentication --> User fetched from Db
    AbstractAuthenticationProcessingFilter  --> doFilter
    AbstractAuthenticationProcessingFilter  --> successfulAuthentication
 */
@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true))
                //.requiresChannel(rcc -> rcc.anyRequest().requiresSecure())   //will accept https only
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/private/balance", "private/message", "/admin/announcement", "/admin/loan").authenticated()
                        .requestMatchers("/public/home", "/public/contact", "/error", "/invalidSession").permitAll()
                        .requestMatchers("/h2-console/*").permitAll()
                );
        http.formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
