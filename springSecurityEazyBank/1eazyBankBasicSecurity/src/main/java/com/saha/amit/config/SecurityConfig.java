package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class SecurityConfig {

    /*
    DefaultLoginPageGeneratingFilter  --> generateLoginPageHtml -->Intercepts the request and shows login page
    AbstractSecurityInterceptor --> authenticateIfRequired /attemptAuthorization  --> Intercept request and check if already authenticated
    UsernamePasswordAuthenticationFilter -->attemptAuthentication Extracts
    UsernamePasswordAuthenticationToken extends --> AbstractAuthenticationToken implements -->Authentication
    ProviderManager(UsernamePasswordAuthenticationToken)  implements --> AuthenticationManager
    DaoAuthenticationProvider. -->So whenever we are using the default authentication provider as a developer, our responsibility is only to load the user details from the storage system
    like database and configure what is a password and code that we want to use.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        //.anyRequest().permitAll()
                        //.anyRequest().authenticated()
                        //.anyRequest().denyAll()
                        .requestMatchers("/private/balance", "private/message", "/admin/announcement", "/admin/loan").authenticated()
                        .requestMatchers("/public/home", "/public/contact", "/error").permitAll()
                )
                //.formLogin(fl->fl.disable())      //When this is disabled instead of showing login form browser shows popup to read credentials and add to header
                //.httpBasic(bs->bs.disable())
                .formLogin(Customizer.withDefaults())          //Enable form based login, credentials will be extracted from UsernamePasswordAuthenticationFilter
                .httpBasic(Customizer.withDefaults());          //credentials inside the httpRequest header by Base64 encoding them, BasicAuthenticationFilter
        return http.build();
    }


    @Bean
    //public UserDetailsManager userDetailsService() { This has additional API for create user, reset pwd etc.
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin").password("{noop}12345").roles("ADMIN").build();
        //https://bcrypt-generator.com/
        UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$HknEwKGJto6O4zTn0pSA6.L9OX2wDEa3beQpN3W5XKrbNCipR0eTm")
                .roles("USER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
