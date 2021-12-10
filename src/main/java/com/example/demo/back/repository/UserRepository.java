package com.example.demo.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.person.UserApp;

@Repository
public interface UserRepository extends CrudRepository<UserApp, Long> {

	
	List<UserApp> findByUsername(String username);
	

}
