package com.example.demo.front.businessdelegate.interfaces;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.springframework.web.client.RestTemplate;

import com.example.demo.front.model.person.*;
import com.example.demo.front.model.sales.*;

public interface BusinessDelegate {
	
	void setRestTemplate(RestTemplate restTemplate);
	
	//BUSINESS ENTITY
	
    List<Businessentity> findAllBusinessentitys();
    Businessentity findBusinessentityById(Integer id);
    void deleteBusinessentity(Businessentity ben);
    Businessentity saveBusinessentity(Businessentity ben);
    void editBusinessentity(Businessentity ben);
	
	//PERSON
	
    List<Person> findAllPersonsByPersonType(String persontype);
    List<Person> findAllPersonsByTitle(String title);
    List<Person> findAllPersonsByBusinessentityid(Integer businessentityid);
    List<Person> findAllPersons();
    Person findPersonById(Integer id);
    void deletePerson(Person person);
    Person savePerson(Person person);
    void editPerson(Person person);
	
    //STATE PROVINCE
	
    List<Stateprovince> findAllStateprovinces();
    Stateprovince findStateprovinceById(Integer id);
    void deleteStateprovince(Stateprovince stprov);
    Stateprovince saveStateprovince(Stateprovince stprov);
    void editStateprovince(Stateprovince stprov);
    
    //ADDRESS
	
    List<Address> findAllAddressByModifiedDate(Timestamp modifieddate);
    List<Address> findAllAddressByStateProvince(Integer stateprovince);
    List<Address> findAllAddresses();
    Address findAddressById(Integer id);
    void deleteAddress(Address addr);
    Address saveAddress(Address addr);
    void editAddress(Address addr);
    
    //ADDRESS TYPE
	
    List<Addresstype> findAllAddresstypes();
    Addresstype findAddresstypeById(Integer id);
    void deleteAddresstype(Addresstype addrtype);
    Addresstype saveAddresstype(Addresstype addrtype);
    void editAddresstype(Addresstype addrtype);	
    
    //PHONE NUMBER TYPE
	
    List<Phonenumbertype> findAllPhonenumbertypes();
    Phonenumbertype findPhonenumbertypeById(Integer id);
    void deletePhonenumbertype(Phonenumbertype phone);
    Phonenumbertype savePhonenumbertype(Phonenumbertype phone);
    void editPhonenumbertype(Phonenumbertype phone);
    
    //PERSONPHONE
	
    List<Personphone> findAllPersonphonesByPhonenumbertype(Integer phonenumbertype);
    List<Personphone> findAllPersonPhonesByPerson(Integer person);
    List<Personphone> findAllPersonphones();
    Personphone findPersonphoneById(PersonphonePK id);
    void deletePersonphone(Personphone personphone);
    Personphone savePersonphone(Personphone personphone);
    void editPersonphone(Personphone personphone);
    
    //BUSINESSENTITYADDRESS
	
    List<Businessentityaddress> findAllBusinessentityaddressByAddress(Integer address);
    List<Businessentityaddress> findAllBusinessentityaddressByAddresstype(Integer addresstype);
    List<Businessentityaddress> findAllBusinessentityaddressByBusinessentity(Integer businessentity);
    List<Businessentityaddress> findAllBusinessentityaddress();
    Businessentityaddress findBusinessentityaddressById(BusinessentityaddressPK id);
    void deleteBusinessentityaddress(Businessentityaddress benaddr);
    Businessentityaddress saveBusinessentityaddress(Businessentityaddress benaddr);
    void editBusinessentityaddress(Businessentityaddress benaddr);
    
    //STORE
	
    List<Store> findAllStores();
    Store findStoreById(Integer id);
    void deleteStore(Store store);
    Store saveStore(Store store);
    void editStore(Store store);
    
    //CUSTOMER
	
    List<Customer> findAllCustomersByPerson(Integer person);
    List<Customer> findAllCustomers();
    Customer findCustomerById(Integer id);
    void deleteCustomer(Customer customer);
    Customer saveCustomer(Customer customer);
    void editCustomer(Customer customer);
}
