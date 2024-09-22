package com.saha.amit;

import com.saha.amit.model.Customer;
import com.saha.amit.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EazyBankCustomAuthenticationProvider implements CommandLineRunner {

    private  final CustomerRepository customerRepository;

    public EazyBankCustomAuthenticationProvider(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        System.out.println("http://localhost:8080/public/home");
        SpringApplication.run(EazyBankCustomAuthenticationProvider.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("johndoe@example.com", "{noop}54321", "user");
        Customer customer1 = new Customer("admin@example.com", "{bcrypt}$2a$12$HknEwKGJto6O4zTn0pSA6.L9OX2wDEa3beQpN3W5XKrbNCipR0eTm",
                "user");

        System.out.println(customerRepository.save(customer));
        System.out.println(customerRepository.save(customer1));
    }
}