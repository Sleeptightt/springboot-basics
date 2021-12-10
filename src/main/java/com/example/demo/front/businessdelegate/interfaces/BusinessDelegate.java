package com.example.demo.front.businessdelegate.interfaces;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.example.demo.front.model.person.*;

public interface BusinessDelegate {
	
	void setRestTemplate(RestTemplate restTemplate);
	
	//PERSON
	
    List<Person> findAllPersonsByPersonType(String persontype);
    List<Person> findAllPersonsByTitle(String title);
    List<Person> findAllPersonsByBusinessentityid(Integer businessentityid);
    List<Person> findAllPersons();
    Person findPersonById(Integer id);
    void deletePerson(Person person);
    Person savePerson(Person person);
    void editPerson(Person person);
	
}
