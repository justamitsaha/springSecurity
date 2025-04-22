package com.saha.amit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
public class EazyBankBackendApplication {

    private final static Log log = LogFactory.getLog(EazyBankBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EazyBankBackendApplication.class, args);
        log.info("PKCE endpoint http://localhost:8080/public/KeyCloakPKCE.html");
        log.info("PKCE endpoint http://localhost:8080/public/AuthorizationCodeGrantflow.html");

    }

}
