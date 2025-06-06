package com.saha.amit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityBasicApplication {

    public static final Log log = LogFactory.getLog(SpringSecurityBasicApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityBasicApplication.class, args);
        log.info("http://localhost:8080/public/home.html");
        log.info("http://localhost:8080/h2-console/login.do");
    }

}