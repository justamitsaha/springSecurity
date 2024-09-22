package com.saha.amit.repository;

import java.util.List;

import com.saha.amit.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.saha.amit.model.Cards;

@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(long customerId);

}
