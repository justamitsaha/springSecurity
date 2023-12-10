package com.saha.amit.controller;

import com.saha.amit.config.CustomAuthenticationProvider;
import com.saha.amit.constants.SecurityConstants;
import com.saha.amit.dto.UserDTO;
import com.saha.amit.model.Customer;
import com.saha.amit.repository.CustomerRepository;
import com.saha.amit.util.JWTTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;
    private final Logger LOG = Logger.getLogger(LoginController.class.getName());
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        System.out.println(customer.getPwd());
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            savedCustomer = customerRepository.save(customer);
            if (savedCustomer.getId() > 0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @PostMapping("/user")
    public ResponseEntity<String> getUserDetailsAfterLogin(@RequestBody UserDTO userDTO) {
        LOG.info("getUserDetailsAfterLogin");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        customAuthenticationProvider.authenticate(token);
        String jwt = JWTTokenGenerator.tokenGenerator(userDTO.getEmail());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(SecurityConstants.JWT_HEADER,jwt);
        return ResponseEntity.status(HttpStatus.OK).headers(responseHeaders).body("OK");
    }

}
