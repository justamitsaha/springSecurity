package com.saha.amit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EazyBankBasicSecurityApplication {

    public static final Log log = LogFactory.getLog(EazyBankBasicSecurityApplication.class);

    public static void main(String[] args) {
        log.info("http://localhost:8080/public/home");
        log.info("http://localhost:8080/h2-console/login.do");
        SpringApplication.run(EazyBankBasicSecurityApplication.class, args);
    }

}