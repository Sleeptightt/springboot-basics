package com.example.demo.back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.person.Phonenumbertype;

@Repository
public interface PhoneNumberTypeRepository extends CrudRepository<Phonenumbertype, Integer>{
	
//	public void savePhoneNumberType(Phonenumbertype phonenumbertype);
//	public Address getPhoneNumberType(Long id);
//	public void deletePhoneNumberType(Phonenumbertype phonenumbertype);
//	public void updatePhoneNumberType(Phonenumbertype phonenumbertype);
}
