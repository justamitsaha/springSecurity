package com.saha.amit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableWebSecurity(debug = true)
public class EazyBankCorsCsrfApplication {
        public static void main(String[] args) {
        SpringApplication.run(EazyBankCorsCsrfApplication.class, args);
    }

}
