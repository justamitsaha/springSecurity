package com.saha.amit.config;

import com.saha.amit.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
public class CustomAuthenticationProvidersConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public CustomAuthenticationProvidersConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    /*
    This Authentication manager is configured as default which means if no qualifier is provided then this will be used
    It uses the default DaoAuthenticationProvider
    To which in memory service is injected
     */
    @Primary
    @Bean(name = "defaultAuthenticationManager")
    public AuthenticationManager authenticationManager(@Qualifier("inMemoryAuthenticationProvider") AuthenticationProvider authenticationProvider ) throws Exception {
        return new ProviderManager(authenticationProvider);
    }


    /**
     * @param userDetailsService In memory
     * @param passwordEncoder default password Encoder
     * @return AuthenticationProvider
     */
    @Bean(name = "inMemoryAuthenticationProvider")
    public AuthenticationProvider inMemoryAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider inMemoryProvider = new DaoAuthenticationProvider();
        inMemoryProvider.setUserDetailsService(userDetailsService);  // this is your in-memory one
        inMemoryProvider.setPasswordEncoder(passwordEncoder);
        return inMemoryProvider;
    }

    /**
     * In-memory user details service with default admin and user accounts.
     * It is used by above manager
     * public UserDetailsManager userDetailsService() { This has additional API for create user, reset pwd etc.
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin").password("{noop}qwerty").roles("ADMIN").build();
        UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$h0qbEfY3fK8Xz4CIpuDHM.MdrkSVeKx8AodPaX5McnAirmbevL/gi")
                .roles("USER").build();     //https://bcrypt-generator.com/  qwerty
        return new InMemoryUserDetailsManager(admin, user);
    }

    /**
     * This is another Authentication manager uses relies on database authentication using below AuthenticationProvider
     * @param authenticationProvider is uses database from customUserDetailsService as shown below
     * @return AuthenticationManager
     */
    @Bean(name = "dbAuthManager")
    public AuthenticationManager dbAuthenticationManager(@Qualifier("databaseAuthenticationProvider") AuthenticationProvider authenticationProvider) {
        return new ProviderManager(List.of(authenticationProvider));
    }

    /*

     */
    @Bean(name ="databaseAuthenticationProvider")
    public AuthenticationProvider dbAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder); // already defined
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
