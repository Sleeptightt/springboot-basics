package com.example.demo.service.interfaces;

import java.util.Optional;

import com.example.demo.model.person.UserApp;
import com.example.demo.model.person.UserType;

public interface UserService {
	public UserApp save(UserApp user);

	public Optional<UserApp> findById(long id);

	public Iterable<UserApp> findAll();

	public void delete(UserApp user);

	public UserType[] getTypes();
}
