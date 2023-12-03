package com.saha.amit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
        http.authorizeRequests()
                .regexMatchers("(/private/.*)").authenticated()
                .antMatchers("/public").permitAll()
                .antMatchers("/admin").hasRole("ADMIN");
        http.formLogin();
        http.httpBasic();
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        //Approach 2 where we use NoOpPasswordEncoder Bean
        //while creating the user details
        UserDetails admin = User.withUsername("admin")
                .password("12345")
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("user")
                .password("12345")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
//      Approach 1 where we use withDefaultPasswordEncoder() method
//		while creating the user details
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("12345")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);



    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
