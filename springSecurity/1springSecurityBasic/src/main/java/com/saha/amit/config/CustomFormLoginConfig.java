package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

@Configuration
public class CustomFormLoginConfig {
    @Bean
    public Customizer<FormLoginConfigurer<HttpSecurity>> formLoginCustomizer() {
        return form -> form
                .loginPage("/public/home.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/public/home.html", false)
                .failureUrl("/public/home.html?error")
                .permitAll();
    }
}
