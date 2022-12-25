package com.saha.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EazyBankJdbcApplication {

    public static void main(String[] args) {
        System.out.println("http://localhost:8080/h2-console/");
        System.out.println("jdbc:h2:mem:blog");
        System.out.println("sa");
        SpringApplication.run(EazyBankJdbcApplication.class, args);
    }
}
