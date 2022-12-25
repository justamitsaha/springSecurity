package com.saha.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EazyBankCorsCSRFApplication {

    public static void main(String[] args) {
        System.out.println("http://localhost:8080/h2-console");
        SpringApplication.run(EazyBankCorsCSRFApplication.class,args);
    }
}
