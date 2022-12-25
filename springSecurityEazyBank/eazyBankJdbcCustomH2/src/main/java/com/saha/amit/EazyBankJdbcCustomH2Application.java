package com.saha.amit;

import com.saha.amit.model.Customer;
import com.saha.amit.model.NewCustomer;
import com.saha.amit.repository.CustomerRepository;
import com.saha.amit.repository.NewCustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EazyBankJdbcCustomH2Application {
    public static void main(String[] args) {
        System.out.println("http://localhost:8080/h2-console");
        System.out.println("Run applicatin from --> http://localhost:8080/public/home");
        SpringApplication.run(EazyBankJdbcCustomH2Application.class,args);
    }


    @Bean
    CommandLineRunner commandLineRunner (CustomerRepository customerRepository, NewCustomerRepository newCustomerRepository) {
        return args -> {
            customerRepository.save(new Customer("johndoe@example.com","12345", "admin"));
            newCustomerRepository.save(new NewCustomer("abc@xyz.com","12345", "admin"));
        };
    }
}
