package com.example.security.springsecurityjwt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		String encoded=bCryptPasswordEncoder.encode("foo");
		System.out.println(encoded);
		
	}

}
