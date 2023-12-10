package com.saha.amit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {




    @GetMapping ("public/home")
    public String getHome() {
        return getPage() +"WELCOME HOME";
    }

    @GetMapping ("public/contact")
    public String contact() {
        return getPage() +"CONTACT US";
    }

    @GetMapping ("private/balance")
    public String account() {
        return getPage() + "YOUR BALANCE IS $1000";
    }

    @GetMapping ("private/message")
    public String messages() {
        return getPage() + "YOU HAVE 99 MESSAGES";
    }

    @GetMapping ("public/announcement")
    public String announcement() {
        return getPage() + "ATTENTION EVERYONE!";
    }

    @GetMapping ("private/loan")
    public String approveLoan() {
        return getPage() + "WANT TO APPROVE THIS?";
    }

    public static String getPage(){
        String sb = "<a href='/public/home'>HOME</a> <br>"+
                "<a href='/public/contact'>CONTACT</a> <br>"+
                "<a href='/private/balance'>BALANCE</a> <br>"+
                "<a href='/private/message'>MESSAGE</a> <br>"+
                "<a href='/public/announcement'>ANNOUNCEMENT</a> <br>"+
                "<a href='/private/loan'>LOAN</a> <br>";
        return sb;
    }
}
