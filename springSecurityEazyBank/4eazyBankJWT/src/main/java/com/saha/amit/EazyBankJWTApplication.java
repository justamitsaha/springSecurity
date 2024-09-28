package com.saha.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
public class EazyBankJWTApplication {

    public static void main(String[] args) {
        System.out.println("http://localhost:8080/h2-console");
        SpringApplication.run(EazyBankJWTApplication.class,args);
    }
}
