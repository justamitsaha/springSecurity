package com.saha.amit.config;


import com.saha.amit.constants.SecurityConstants;
import com.saha.amit.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        List<String> origins = new ArrayList<>();
        origins.add("http://localhost:4200");
        origins.add("http://localhost");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //Telling Spring security not to create session id
        /*Configuration to run from local */
        http.securityContext().requireExplicitSave(false)
                .and().cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        //config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedOrigins(origins);
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList(SecurityConstants.JWT_HEADER));  //To set headers in different domain
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and().csrf().ignoringAntMatchers("/contact", "/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class).authorizeHttpRequests()
//                .antMatchers("/myAccount").hasRole("USER")
//                .antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/myLoans").hasRole("USER")
//                .antMatchers("/myCards").hasRole("USER")
                .antMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
                .antMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
                .antMatchers("/myLoans").hasAuthority("VIEWLOANS")
                .antMatchers("/myCards").hasAuthority("VIEWCARDS")
                .antMatchers("/user").authenticated()
                .antMatchers("/notices", "/contact", "/register").permitAll()
                .and().formLogin()
                .and().httpBasic();


//        http.securityContext().requireExplicitSave(false)
//                .and().csrf().ignoringAntMatchers("/contact", "/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and().addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class).authorizeHttpRequests()
//                .antMatchers("/myAccount").hasRole("USER")
//                .antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/myLoans").hasRole("USER")
//                .antMatchers("/myCards").hasRole("USER")
//                .antMatchers("/user").authenticated()
//                .antMatchers("/notices", "/contact", "/register").permitAll()
//                .and().formLogin()
//                .and().httpBasic();


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
