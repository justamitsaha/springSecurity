package com.saha.amit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {


    @GetMapping("public/home")
    public String getHome() {
        return getPage() + "WELCOME HOME";
    }

    @GetMapping("public/contact")
    public String contact() {
        return getPage() + "CONTACT US";
    }

    @GetMapping("public/myLogin")
    public ResponseEntity<String> myLogin() {
        return ResponseEntity.ok(loginPage());
    }


    @GetMapping("private/balance")
    public String account() {
        return getPage() + "YOUR BALANCE IS $1000";
    }

    @GetMapping("private/message")
    public String messages() {
        return getPage() + "YOU HAVE 99 MESSAGES";
    }

    @GetMapping("admin/announcement")
    public String announcement() {
        return getPage() + "ATTENTION EVERYONE!";
    }

    @GetMapping("admin/loan")
    public String approveLoan() {
        return getPage() + "WANT TO APPROVE THIS?";
    }

    public static String getPage() {
//        return "<a href='/public/home'>HOME</a> <br>"+
//                "<a href='/public/contact'>CONTACT</a> <br>"+
//                "<a href='/public/myLogin'>My Login</a> <br>"+
//                "<a href='/private/balance'>BALANCE</a> <br>"+
//                "<a href='/private/message'>MESSAGE</a> <br>"+
//                "<a href='/admin/announcement'>ANNOUNCEMENT</a> <br>"+
//                "<a href='/admin/loan'>LOAN</a> <br>";

        return loginPage();
    }


    public static String loginPage() {
        return """
                    <html>
                    <head>
                        <title>Custom Login</title>
                        <script>
                            window.onload = function() {
                                // Show JSESSIONID if available
                                const cookies = document.cookie.split(';');
                                const sessionCookie = cookies.find(c => c.trim().startsWith('JSESSIONID='));
                                if (sessionCookie) {
                                    const sessionId = sessionCookie.split('=')[1];
                                    document.getElementById('sessionIdDisplay').innerText = sessionId;
                                } else {
                                    document.getElementById('sessionIdDisplay').innerText = 'No session';
                                }

                                // Check URL params for error or logout
                                const urlParams = new URLSearchParams(window.location.search);
                                if (urlParams.has('error')) {
                                    document.getElementById('status').innerText = '❌ Login failed. Please try again.';
                                    document.getElementById('status').style.color = 'red';
                                }
                                if (urlParams.has('logout')) {
                                    document.getElementById('status').innerText = '✅ You have been logged out.';
                                    document.getElementById('status').style.color = 'green';
                                }
                            };

                            function clearSession() {
                                document.cookie = "JSESSIONID=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
                                document.getElementById('sessionIdDisplay').innerText = 'Session Cleared';
                            }
                        </script>
                    </head>
                    <body>
                        <h2>Login Page</h2>

                        <!-- This form mimics Spring's default login page -->
                        <form method="POST" action="/login">
                            <label>Username:</label>
                            <input type="text" name="username" required><br>

                            <label>Password:</label>
                            <input type="password" name="password" required><br><br>

                            <button type="submit">Login</button>
                        </form>

                        <p id="status"></p>

                        <h3>Session Info</h3>
                        <p>JSESSIONID: <span id="sessionIdDisplay"></span></p>
                        <button onclick="clearSession()">Clear JSESSIONID</button>

                        <hr>

                        <a href='/public/home'>HOME</a> <br>
                        <a href='/public/contact'>CONTACT</a> <br>
                        <a href='/private/balance'>BALANCE</a> <br>
                        <a href='/private/message'>MESSAGE</a> <br>
                        <a href='/admin/announcement'>ANNOUNCEMENT</a> <br>
                        <a href='/admin/loan'>LOAN</a> <br>
                    </body>
                    </html>
                """;
    }


    public static String loginPage2() {
        return """
                <html>
                                    <head>
                                        <title>Custom Login</title>
                                    </head>
                                    <body>
                                        <h2>Login Page</h2>
                                        <form id="loginForm">
                                            <label>Username:</label>
                                            <input type="text" id="username" required><br>
                                            <label>Password:</label>
                                            <input type="password" id="password" required><br><br>
                                            <button type="submit">Login</button>
                                        </form>
                                        <p id="status"></p>
                                        <a href='/public/home'>HOME</a> <br>
                                        <a href='/public/contact'>CONTACT</a> <br>
                                        <a href='/private/balance'>BALANCE</a> <br>
                                        <a href='/private/message'>MESSAGE</a> <br>
                                        <a href='/admin/announcement'>ANNOUNCEMENT</a> <br>
                                        <a href='/admin/loan'>LOAN</a> <br>
                                        <script>
                                            document.getElementById('loginForm').addEventListener('submit', async function(e) {
                                                e.preventDefault();
                                                const username = document.getElementById('username').value;
                                                const password = document.getElementById('password').value;
                                
                                                const response = await fetch('/login', {
                                                    method: 'POST',
                                                    headers: {
                                                        'Content-Type': 'application/x-www-form-urlencoded'
                                                    },
                                                    body: new URLSearchParams({
                                                        'username': username,
                                                        'password': password
                                                    }),
                                                    credentials: 'include' // Important: ensures cookies (JSESSIONID) are stored
                                                });
                                
                                                if (response.ok) {
                                                    document.getElementById('status').innerText = 'Login successful! Try accessing protected pages.';
                                                } else {
                                                    document.getElementById('status').innerText = 'Login failed. Try again.';
                                                }
                                            });
                                        </script>
                                    </body>
                                    </html>
                """;
    }
}
