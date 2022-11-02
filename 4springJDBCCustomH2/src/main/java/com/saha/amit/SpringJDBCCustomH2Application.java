package com.saha.amit;

import com.saha.amit.model.Customer;
import com.saha.amit.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringJDBCCustomH2Application {
    public static void main(String[] args) {
        System.out.println("http://localhost:8080/h2-console");
        SpringApplication.run(SpringJDBCCustomH2Application.class,args);
    }


    @Bean
    CommandLineRunner commandLineRunner (CustomerRepository customerRepository) {
        return args -> {
            customerRepository.save(new Customer("johndoe@example.com","12345", "admin"));
        };
    }
}
