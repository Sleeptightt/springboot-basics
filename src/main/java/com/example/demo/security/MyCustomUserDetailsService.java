package com.example.demo.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.person.UserApp;
import com.example.demo.repository.UserRepository;

import lombok.extern.java.Log;

@Log
@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public MyCustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserApp> userApps = userRepository.findByUsername(username);
		
		if (userApps != null) {
			log.info("found");
			UserApp userApp = userApps.get(0);
			User.UserBuilder builder = User.withUsername(username).password(userApp.getUserPassword()).roles(userApp.getUserType().toString());
			return builder.build();
		} else {
			log.info("not found");
			throw new UsernameNotFoundException("User not found.");
		}
	}
}