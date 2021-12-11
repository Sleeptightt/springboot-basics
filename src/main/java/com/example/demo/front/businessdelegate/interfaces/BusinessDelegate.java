package com.example.demo.front.businessdelegate.interfaces;

import java.sql.Timestamp;
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
	
    //ADDRESS
	
    List<Address> findAllAddressByModifiedDate(Timestamp modifieddate);
    List<Address> findAllAddressByStateProvince(Integer stateprovince);
    List<Address> findAllAddresses();
    Address findAddressById(Integer id);
    void deleteAddress(Address addr);
    Address saveAddress(Address addr);
    void editAddress(Address addr);
    
    //PERSONPHONE
	
    List<Personphone> findAllPersonphonesByPhonenumbertype(Integer phonenumbertype);
    List<Personphone> findAllPersonPhonesByPerson(Integer person);
    List<Personphone> findAllPersonphones();
    Personphone findPersonphoneById(PersonphonePK id);
    void deletePersonphone(Personphone personphone);
    Personphone savePersonphone(Personphone personphone);
    void editPersonphone(Personphone personphone);
}
