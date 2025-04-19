package com.saha.amit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
        return "YOUR BALANCE IS $1000";
    }

    @GetMapping("private/message")
    public String messages() {
        return "YOU HAVE 99 MESSAGES";
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
    public String securePost(){
        return "Secure";
    }

    @PostMapping("/public/publicUpdate")
    public String insecurePost(){
        return "Public";
    }

}
