package com.userAuth.app.dto;

import lombok.Data;

@Data
public class CustomerRequest {
	private String name;
	private String username;
	private String email;
	private String password;

}
