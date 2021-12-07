package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.person.UserApp;
import com.example.demo.model.person.UserType;

@Repository
public interface UserRepository extends CrudRepository<UserApp, Long> {

	
	List<UserApp> findByUsername(String username);
	

}
