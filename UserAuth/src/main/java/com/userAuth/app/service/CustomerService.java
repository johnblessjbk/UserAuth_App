package com.userAuth.app.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userAuth.app.dto.CustomerRequest;
import com.userAuth.app.entities.Customer;
import com.userAuth.app.entities.Login;
import com.userAuth.app.entities.Role;
import com.userAuth.app.repository.CustomerRepo;
import com.userAuth.app.repository.LoginRepo;
import com.userAuth.app.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private LoginRepo loginRepo;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	public boolean addUserData(Customer customerRequest) {
		validateUser(customerRequest);
		Customer cus = new Customer();
		BeanUtils.copyProperties(customerRequest, cus);
		cus.setPassword(passwordEncoder.encode(cus.getPassword()));
		customerRepo.save(cus);

		cus.getLogin().setUser(cus);
        cus.getLogin().setPassword(passwordEncoder.encode(cus.getLogin().getPassword())); // Encode login password

		loginRepo.save(customerRequest.getLogin());
		return true;
	}
	private void validateUser(Customer user) {
		if (customerRepo.existsByName(user.getName())) {
			throw new IllegalArgumentException("UserName: Username is already taken!");
		}
		if (customerRepo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email: Email is already in use!");
		}
	}
	public Login authenticate(CustomerRequest input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return loginRepo.findByEmail(input.getEmail()).orElseThrow();
	}
	
	public boolean addRole(Role role) {
		roleRepository.save(role);
		return true;
	}
}
