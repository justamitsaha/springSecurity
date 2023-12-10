package com.saha.amit.config;


import com.saha.amit.filter.CsrfCookieFilter;
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
import java.util.Collections;
import java.util.List;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        List<String> origins = new ArrayList<>();
        origins.add("http://localhost:4200");
        origins.add("http://localhost");

        /**
         *  From Spring Security 6, below actions will not happen by default,
         *  1) The Authentication details will not be saved automatically into SecurityContextHolder. To change this behaviour either we need to save
         *      these details explicitly into SecurityContextHolder or we can configure securityContext().requireExplicitSave(false) like shown below.
         *  2) The Session & JSessionID will not be created by default. Inorder to create a session after intial login, we need to configure
         *      sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) like shown below.
         */

        http.securityContext().requireExplicitSave(false)
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))//Create J-session id and send to UIa application every time
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        //config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedOrigins(origins);
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                    /**
                     *  From Spring Security 6, by default the CSRF Cookie that got generated during intial login will not be shared to UI application.
                     *  The developer has to write logic to read the CSRF token and send it as part of the response. When framework sees the
                     *  CSRF token in the response header, it takes of sending the same as Cookie as well. For the same, I have created
                     *  a filter with the name CsrfCookieFilter and configured the same to run every time after the Spring Security in built filter
                     *  BasicAuthenticationFilter like shown below. More details about Filters, are discussed inside the Section 8 of the course.
                     */
                }).and().csrf().ignoringAntMatchers("/contact", "/register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                //.antMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
                .antMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
                .antMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
                .antMatchers("/myLoans").hasAuthority("VIEWLOANS")
                .antMatchers("/myCards").hasAuthority("VIEWCARDS")
                .antMatchers("/user").authenticated()
                .antMatchers("/notices", "/contact", "/register").permitAll()
                .and().formLogin()
                .and().httpBasic();
        return http.build();

//        http.securityContext().requireExplicitSave(false)
//                .and().cors().configurationSource(new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowedOrigins(origins);
//                        config.setAllowedMethods(Collections.singletonList("*"));
//                        config.setAllowCredentials(true);
//                        config.setAllowedHeaders(Collections.singletonList("*"));
//                        config.setMaxAge(36000L);
//                        return config;
//                    }
//                }).and().csrf().ignoringAntMatchers("/contact", "/register").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and().authorizeHttpRequests()
//                .antMatchers("/myAccount").hasRole("USER")
//                .antMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
//                .antMatchers("/myLoans").hasRole("USER")
//                .antMatchers("/myCards").hasRole("USER")
//                .antMatchers("/user").authenticated()
//                .antMatchers("/notices", "/contact", "/register").permitAll()
//                .and().formLogin()
//                .and().httpBasic();
//        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
