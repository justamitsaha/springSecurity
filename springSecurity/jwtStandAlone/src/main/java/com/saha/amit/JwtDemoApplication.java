package com.saha.amit;

import com.saha.amit.util.JwtTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class JwtDemoApplication implements CommandLineRunner {

    @Autowired
    private JwtTest jwtTest;

    public static void main(String[] args) {
        SpringApplication.run(JwtDemoApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        jwtTest.testGenerateTokenWithoutAge();
        jwtTest.testGenerateTokenWithAge();
//        jwtTest.testValidateToken();
//        jwtTest.testExtractInformationFromToken();
//        jwtTest.testShouldRefreshToken();
//        jwtTest.testRefreshToken();
//        jwtTest.testValidateInvalidToken();
        System.out.println("\n======= JWT Demo Completed =======");
    }
}