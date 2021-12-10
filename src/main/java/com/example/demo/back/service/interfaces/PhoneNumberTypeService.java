package com.example.demo.back.service.interfaces;

import java.util.Optional;

import com.example.demo.front.model.person.Phonenumbertype;

public interface PhoneNumberTypeService {
	
	public Iterable<Phonenumbertype> findAll();
	
	public Phonenumbertype savePhoneNumberType(Phonenumbertype phonetype);

	public Optional<Phonenumbertype> findById(long id);
	
	public void delete(Phonenumbertype phonetype);

	public Phonenumbertype updatePhoneNumberType(Phonenumbertype phonetype);
	
	
}
