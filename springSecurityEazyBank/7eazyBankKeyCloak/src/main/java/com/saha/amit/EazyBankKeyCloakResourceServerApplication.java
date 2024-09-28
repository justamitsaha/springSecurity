package com.saha.amit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,  securedEnabled = true,  jsr250Enabled = true)
public class EazyBankKeyCloakResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankKeyCloakResourceServerApplication.class, args);
	}

}
