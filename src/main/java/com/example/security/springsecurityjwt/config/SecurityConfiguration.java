package com.example.security.springsecurityjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.springsecurityjwt.constants.SecurityConstants;
import com.example.security.springsecurityjwt.filters.JwtRequestFilter;
import com.example.security.springsecurityjwt.security.UnauthorizedEntryPoint;
import com.example.security.springsecurityjwt.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private UnauthorizedEntryPoint unauthorizedEntryPoint;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	


	//Since Spring-security 5.7.0-M2 we can no longer extend the WebSecurityConfigurerAdapter class , instead we create
	//component-based security configuration by registering SecurityFilterChain bean as shown below
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
//		// Configure AuthenticationManagerBuilder
//	     AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//	     
//	     //Add userDetailsService implementation and passwordEncoder.
//	     authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	     
//	     // Get AuthenticationManager
//	     AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
		
		http.cors().and().csrf().disable().authorizeRequests()
						.antMatchers(SecurityConstants.LOGIN_URL, SecurityConstants.SIGN_UP_URL)
						.permitAll()
						.anyRequest().authenticated()
						.and()
		                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration  authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
