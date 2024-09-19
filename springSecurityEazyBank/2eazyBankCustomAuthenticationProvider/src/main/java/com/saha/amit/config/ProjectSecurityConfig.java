package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .anyRequest().permitAll()
//                        .requestMatchers("/private/.*").authenticated()
//                        .requestMatchers("/public/*").permitAll()
//                        .requestMatchers("/h2-console/*").permitAll()
                ).formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
       /* http.csrf().disable()
                .authorizeHttpRequests()
                .regexMatchers("(/private/.*)").authenticated()
                .antMatchers("/public").permitAll()
                .and().formLogin()
                .and().httpBasic();*/
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
