package com.example.demo.service.interfaces;

import java.util.Optional;

import com.example.demo.model.person.Address;
import com.example.demo.model.person.Addresstype;
import com.example.demo.model.person.Businessentity;
import com.example.demo.model.person.Businessentityaddress;
import com.example.demo.model.person.BusinessentityaddressPK;
import com.example.demo.model.person.Person;
import com.example.demo.model.person.Personphone;
import com.example.demo.model.person.PersonphonePK;
import com.example.demo.model.person.Phonenumbertype;

public interface PersonPhoneService {
	
	public Iterable<Personphone> findAll();
	
	public Personphone savePersonPhone(Personphone benphone);

	public Optional<Personphone> findById(PersonphonePK id);
	
	public void delete(Personphone benphone);

	public Personphone updatePersonPhone(Personphone benphone);
	
	public Iterable<Personphone> findAllByPerson(Person person);
	
	public Iterable<Personphone> findAllByPhoneNumbertype(Phonenumbertype phonetype);
	
	
	
}
