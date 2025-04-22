package com.saha.amit.authserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthserverApplication {

	static Log log = LogFactory.getLog(AuthserverApplication.class);
	public static void main(String[] args) {
		log.info("http://localhost:9000/.well-known/openid-configuration");
		SpringApplication.run(AuthserverApplication.class, args);
	}

}
