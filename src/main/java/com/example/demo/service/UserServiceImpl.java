package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.person.UserApp;
import com.example.demo.model.person.UserType;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.UserService;


@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserApp save(UserApp user) {
		return userRepository.save(user);
	}

	public Optional<UserApp> findById(long id) {

		return userRepository.findById(id);
	}

	public Iterable<UserApp> findAll() {
		return userRepository.findAll();
	}


	public void delete(UserApp user) {
		userRepository.delete(user);

	}

	public UserType[] getTypes() {
		// TODO Auto-generated method stub
		return UserType.values();
	}

	
}

