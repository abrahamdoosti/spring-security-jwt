package com.example.security.springsecurityjwt;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.springsecurityjwt.model.AuthenticationRequest;
import com.example.security.springsecurityjwt.model.AuthenticationResponse;
import com.example.security.springsecurityjwt.services.MyUserDetailsService;
import com.example.security.springsecurityjwt.util.JwtUtil;

@RestController
public class HelloController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService userDetailService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@GetMapping("/hello")
	//@PreAuthorize("hasRole('Admin')")
	public String hello(Principal principal) {
		return "hello "+principal.getName()+"!";
	}

	@PostMapping("/users/authenticate")
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(
			@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword(), new ArrayList()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUserName());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}

}
