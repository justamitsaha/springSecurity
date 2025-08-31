package com.saha.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecuritySocialLoginOAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecuritySocialLoginOAuthApplication.class, args);
        System.out.print("http://localhost:8080/secure");
    }
}
