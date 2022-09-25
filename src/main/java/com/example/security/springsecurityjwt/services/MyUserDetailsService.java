package com.example.security.springsecurityjwt.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// We can fetch the user info from whatever source we want, 
		// just use the user data you fetched to construct the spring User object and return it.
		List<GrantedAuthority> authorities=new ArrayList<>();
		List<String> stubAuthorities=Arrays.asList("Admin","Editor","User");
		for(String str:stubAuthorities ) {
			authorities.add(new SimpleGrantedAuthority(str));
		}
		return new User("foo","$2a$10$g0BhRuKJLiW.csU20RZsBuNl6vywa8sDXaONPoOyvav3jlQeNgrZC",authorities);
	}

}
