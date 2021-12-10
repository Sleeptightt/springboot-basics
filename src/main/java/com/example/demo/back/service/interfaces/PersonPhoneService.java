package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;
import com.example.demo.front.model.person.Phonenumbertype;

public interface PersonPhoneService {
	
	public Iterable<Personphone> findAll();
	
	public Personphone savePersonPhone(Personphone benphone);

	public Optional<Personphone> findById(PersonphonePK id);
	
	public void delete(Personphone benphone);

	public Personphone updatePersonPhone(Personphone benphone);
	
	public Iterable<Personphone> findAllByPerson(Person person);
	
	public Iterable<Personphone> findAllByPhoneNumbertype(Phonenumbertype phonetype);
	
	
	
}
