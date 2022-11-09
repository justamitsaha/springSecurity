package com.saha.amit.repository;

import com.saha.amit.model.NewCustomer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewCustomerRepository extends CrudRepository<NewCustomer,Long> {
    List<NewCustomer> findByEmail(String email);
}
