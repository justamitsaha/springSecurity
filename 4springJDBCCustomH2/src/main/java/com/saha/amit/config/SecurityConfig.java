package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeRequests(auth -> auth
//                        .regexMatchers("(/private/.*)").authenticated()
//                        .antMatchers("/public","/h2-console/**").permitAll()
//                )
//                .headers(headers -> headers.frameOptions().sameOrigin())
//                .build();

        http.csrf().disable()
                .authorizeRequests(auth -> auth
                        .regexMatchers("(/private/.*)").authenticated()
                        .antMatchers("/public", "/h2-console/**").permitAll())
                .headers(headers -> headers.frameOptions().sameOrigin());
        http.formLogin();
        http.httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
