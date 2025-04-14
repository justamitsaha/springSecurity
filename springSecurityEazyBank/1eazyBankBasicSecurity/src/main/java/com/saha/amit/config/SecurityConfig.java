package com.saha.amit.config;

import com.saha.amit.filter.*;
import com.saha.amit.service.CustomUserDetailsService;
import com.saha.amit.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Lazy
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http ,
                                            @Qualifier("defaultAuthenticationManager") AuthenticationManager defaultAuthManager,
                                            @Qualifier("dbAuthManager") AuthenticationManager dbAuthManager,
                                            JwtUtil jwtUtil) throws Exception {

        JsonUsernamePasswordAuthenticationFilter jsonAuthFilter = new JsonUsernamePasswordAuthenticationFilter(defaultAuthManager);
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(defaultAuthManager, jwtUtil);
        DataBaseLoginFilter dbLoginFilter = new DataBaseLoginFilter(dbAuthManager, jwtUtil);
        DataBaseAuthorizationFilter dbAuthorizationFilter = new DataBaseAuthorizationFilter(jwtUtil);

        jsonAuthFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Login successful JsonUsernamePasswordAuthenticationFilter \"}");
        });

        jsonAuthFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Login failed JsonUsernamePasswordAuthenticationFilter\"}");
        });


        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/favicon.ico", "/public/style.css", "/public/main.js", "/images/**","/public/home.html", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/api/login", "/v2/api/login", "/v3/api/login").permitAll()
                        .requestMatchers("/private/balance", "/private/message", "/admin/announcement", "/admin/loan").authenticated()
                        .requestMatchers("/public/home", "/public/contact", "/error", "/public/myLogin","/public/me").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/public/home.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/public/home.html", false)
                        .failureUrl("/public/home.html?error")
                        .permitAll()
                )
                .authenticationManager(defaultAuthManager) // ✅ Forces form login to use in-memory auth only
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // ✅ for H2 frames
                .csrf(csrf -> csrf.disable())                                        // ✅ for H2 POSTs
                .addFilterAt(jsonAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(dbLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(dbAuthorizationFilter, JwtAuthorizationFilter.class)
                .httpBasic(Customizer.withDefaults())          //credentials inside the httpRequest header by Base64 encoding them, BasicAuthenticationFilter
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    //public UserDetailsManager userDetailsService() { This has additional API for create user, reset pwd etc.
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin").password("{noop}qwerty").roles("ADMIN").build();
        UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$G2mI8xcwWVLfpkW9r.sz.OaKPoSQWc4cpM5gfP7JVe3ZAY7h9k.q2")
                .roles("USER").build();     //https://bcrypt-generator.com/  qwerty
        return new InMemoryUserDetailsManager(admin, user);
    }


    @Primary
    @Bean(name = "defaultAuthenticationManager")
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        DaoAuthenticationProvider inMemoryProvider = new DaoAuthenticationProvider();
        inMemoryProvider.setUserDetailsService(userDetailsService());  // this is your in-memory one
        inMemoryProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(inMemoryProvider);
    }

    @Bean(name = "dbAuthManager")
    public AuthenticationManager dbAuthenticationManager() {
        return new ProviderManager(List.of(dbAuthenticationProvider()));
    }

    @Bean
    public DaoAuthenticationProvider dbAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder()); // already defined
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }

}
