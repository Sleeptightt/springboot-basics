package com.example.demo.service.interfaces;

import java.util.Optional;

import com.example.demo.model.person.Businessentity;
import com.example.demo.model.person.Person;

public interface PersonService {

	public Iterable<Person> findAll();
	
	public Person savePerson(Person person);

	public Optional<Person> findById(long id);
	
	public void delete(Person person);

	public Person updatePerson(Person person);
}
