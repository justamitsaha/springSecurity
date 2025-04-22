package com.saha.amit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
public class MainController {

    @GetMapping("public/me")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println(authentication.getName());
            return ResponseEntity.ok(Map.of("username", authentication.getName()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
    }


    @GetMapping("public/home")
    public String getHome() {
        return "WELCOME HOME";
    }

    @GetMapping("public/contact")
    public String contact() {
        return "CONTACT US";
    }

    @GetMapping("private/balance")
    public String account() {
        Random random = new Random();
        int balance = random.nextInt(1000, 99999);
        return "YOUR BALANCE IS $" + balance;
    }

    @GetMapping("private/message")
    public String messages() {
        Random random = new Random();
        int message = random.nextInt(50, 100);
        return "YOU HAVE " + message + " MESSAGES";
    }

    @GetMapping("admin/announcement")
    public String announcement() {
        return "ATTENTION EVERYONE!";
    }

    @GetMapping("admin/loan")
    public String approveLoan() {
        return "WANT TO APPROVE THIS?";
    }

    @PostMapping("/private/protectedUpdate")
    public String securePost() {
        return "Secure POST response";
    }

    @PostMapping("/public/publicUpdate")
    public String insecurePost() {
        return "Public POST response";
    }

}
