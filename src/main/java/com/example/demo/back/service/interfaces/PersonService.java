package com.example.demo.back.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.example.demo.front.model.person.Person;

public interface PersonService {

	public Iterable<Person> findAll();
	
	public Person savePerson(Person person);

	public Optional<Person> findById(long id);
	
	public void delete(Integer id);

	public Person updatePerson(Person person);
	
	public List<Person> findAllByPersontype(String persontype);
	
    public List<Person> findAllByTitle(String title);
	
    public List<Person> findAllByBusinessentityid(Integer businessentity);
}
