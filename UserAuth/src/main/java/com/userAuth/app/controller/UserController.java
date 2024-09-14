package com.userAuth.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userAuth.app.dto.CustomerRequest;
import com.userAuth.app.dto.LoginResponse;
import com.userAuth.app.entities.Customer;
import com.userAuth.app.entities.Login;
import com.userAuth.app.entities.Role;
import com.userAuth.app.jwt.JwtService;
import com.userAuth.app.service.CustomerService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private CustomerService customerSrv;
	@Autowired
	private JwtService jwtService;

	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody Customer customerReq) {
		if (customerSrv.addUserData(customerReq)) {
			return ResponseEntity.status(HttpStatus.OK).body("User Created Succesfully");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Created User");

		}
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody CustomerRequest loginreq) {
		Login authenticatedUser = customerSrv.authenticate(loginreq);
		String jwtToken = jwtService.generateToken(authenticatedUser);
		LoginResponse loginResponse = new LoginResponse().setToken(jwtToken)
				.setExpiresIn(jwtService.getExpirationTime());
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
	}

	@PostMapping("/addRole")
	public ResponseEntity<String> addRole(@RequestBody Role role) {
		if (customerSrv.addRole(role)) {
			return ResponseEntity.status(HttpStatus.OK).body("Role Created Succesfully");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Create Role");
	}
}
