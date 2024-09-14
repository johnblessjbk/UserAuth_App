package com.userAuth.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userAuth.app.entities.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{

	boolean existsByEmail(String email);

	boolean existsByName(String name);


}
