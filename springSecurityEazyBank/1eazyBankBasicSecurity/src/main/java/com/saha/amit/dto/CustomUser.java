package com.saha.amit.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*
Here we are using the Entity table as UserDetails but we should avoid this
1. Tight Coupling with DB Layer
2. Overexposing Sensitive Data
3. Not Fully Aligned with SRP (Single Responsibility Principle)
4. Hibernate Lazy Initialization Issues (if using entity)

 */

@Getter
@Setter
@Entity
@Table(name = "custom_user")
public class CustomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int age;

    private String roles; // comma-separated, e.g., "USER,ADMIN"

    @JsonIgnore
    @OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
    private Set<Authority> authorities;

    // --- UserDetails Methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Arrays.stream(roles.split(","))
//                .map(String::trim)
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // --- Custom Fields ---
}


