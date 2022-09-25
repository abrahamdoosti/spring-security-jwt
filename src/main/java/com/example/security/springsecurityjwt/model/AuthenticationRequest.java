package com.example.security.springsecurityjwt.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationRequest {
	private String userName;
	private String password;
	public AuthenticationRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

}
