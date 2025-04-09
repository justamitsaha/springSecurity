package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    //.formLogin(fl->fl.disable())      //When this is disabled instead of showing login form browser shows popup to read credentials and add to header
    //.httpBasic(bs->bs.disable())
    //.formLogin(Customizer.withDefaults())          //Enable form based login, credentials will be extracted from UsernamePasswordAuthenticationFilter
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**","/public/home.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers("/private/balance", "/private/message", "/admin/announcement", "/admin/loan").authenticated()
                        .requestMatchers("/public/home", "/public/contact", "/error", "/public/myLogin","/public/me").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/public/myLogin")   // <--- TELL SPRING TO USE THIS PAGE
                        .loginProcessingUrl("/login") // where the form posts to
                        .defaultSuccessUrl("/public/home.html", true) // optional: redirect after login
                        .failureUrl("/public/myLogin?error")     // optional: redirect on failure
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())          //credentials inside the httpRequest header by Base64 encoding them, BasicAuthenticationFilter
                .csrf(csrf -> csrf.disable());
        return http.build();
    }


    @Bean
    //public UserDetailsManager userDetailsService() { This has additional API for create user, reset pwd etc.
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin").password("{noop}Ghd__reb").roles("ADMIN").build();
        //https://bcrypt-generator.com/  Ghd__reb
        UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$goSiFgLDT7UP9iVOh5nPWe4Riah20ujHbsjv0c9MtPdYdgoszv/.G")
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
