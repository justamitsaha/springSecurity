package com.saha.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EazyBankCustomAuthenticationProvider {
    public static void main(String[] args) {
        System.out.println("http://localhost:8080/public/home");
        SpringApplication.run(EazyBankCustomAuthenticationProvider.class, args);
    }
}