package com.example.demo.front.businessdelegate.implementation;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.front.businessdelegate.interfaces.BusinessDelegate;
import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;

@Component
public class BusinessDelegateImpl implements BusinessDelegate{

	private final static String URL = "http://localhost:8080/api";

	private final static String ADDR_URL = URL + "/addr/";
    private final static String PER_URL = URL + "/person/";
    private final static String PHONE_URL = URL + "/benphone/";
	
	private RestTemplate restTemplate;
	
	public BusinessDelegateImpl() {
        this.restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
    }
	
	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	// Person --------------------------------------------------------------------------------------------
	
	@Override
	public List<Person> findAllPersonsByPersonType(String persontype) {
		Person[] array = restTemplate.getForObject(PER_URL+
                "search/findAllByPersonType?persontype=" + persontype, Person[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Person> findAllPersonsByTitle(String title) {
		Person[] array = restTemplate.getForObject(PER_URL+
                "search/findAllByTitle?title=" + title, Person[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Person> findAllPersonsByBusinessentityid(Integer businessentityid) {
		Person[] array = restTemplate.getForObject(PER_URL+
                "search/findAllByBusinessEntity?businessentity=" + businessentityid, Person[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Person> findAllPersons() {
		Person[] array = restTemplate.getForObject(PER_URL, Person[].class);
		
		return Arrays.asList(array);
	}

	@Override
	public Person findPersonById(Integer id) {
		return restTemplate.getForObject(PER_URL+id, Person.class);
	}

	@Override
	public void deletePerson(Person person) {
		restTemplate.delete(PER_URL+person.getBusinessentityid());
	}

	@Override
	public Person savePerson(Person person) {
		HttpEntity<Person> request = new HttpEntity<>(person);
        return restTemplate.postForObject(PER_URL, request, Person.class);
	}

	@Override
	public void editPerson(Person person) {
		restTemplate.put(PER_URL, person, Person.class);
	}

	//Address --------------------------------------------------------------------------------------------
	
	@Override
	public List<Address> findAllAddressByModifiedDate(Timestamp modifieddate) {
		String format = "yyyy-MM-dd HH:mm:ss.SSS";
		DateFormat formatter = new SimpleDateFormat(format);
		Address[] array = restTemplate.getForObject(ADDR_URL+
                "search/findAllByModifieddate?modifieddate=" + formatter.format(modifieddate), Address[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Address> findAllAddressByStateProvince(Integer stateprovince) {
		Address[] array = restTemplate.getForObject(ADDR_URL+
                "search/findAllByStateProvince?stateprovince=" + stateprovince, Address[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Address> findAllAddresses() {
		Address[] array = restTemplate.getForObject(ADDR_URL, Address[].class);
		
		return Arrays.asList(array);
	}

	@Override
	public Address findAddressById(Integer id) {
		return restTemplate.getForObject(ADDR_URL+id, Address.class);
	}

	@Override
	public void deleteAddress(Address addr) {
		restTemplate.delete(ADDR_URL+addr.getAddressid());
	}

	@Override
	public Address saveAddress(Address addr) {
		HttpEntity<Address> request = new HttpEntity<>(addr);
        return restTemplate.postForObject(ADDR_URL, request, Address.class);
	}

	@Override
	public void editAddress(Address addr) {
		restTemplate.put(ADDR_URL, addr, Address.class);
	}

	//Personphone --------------------------------------------------------------------------------------------
	
	@Override
	public List<Personphone> findAllPersonphonesByPhonenumbertype(Integer phonenumbertype) {
		Personphone[] array = restTemplate.getForObject(PHONE_URL+
                "search/findAllByPhoneNumberType?phonenumbertype=" + phonenumbertype, Personphone[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Personphone> findAllPersonPhonesByPerson(Integer person) {
		Personphone[] array = restTemplate.getForObject(PHONE_URL+
                "search/findAllByPerson?person=" + person, Personphone[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Personphone> findAllPersonphones() {
		Personphone[] array = restTemplate.getForObject(PHONE_URL, Personphone[].class);
		
		return Arrays.asList(array);
	}

	@Override
	public Personphone findPersonphoneById(PersonphonePK id) {
		return restTemplate.getForObject(PHONE_URL+id.getBusinessentityid()+"&"+id.getPhonenumbertypeid(), Personphone.class);
	}

	@Override
	public void deletePersonphone(Personphone personphone) {
		restTemplate.delete(PHONE_URL+personphone.getId().getBusinessentityid()+"&"+personphone.getId().getPhonenumbertypeid());
	}

	@Override
	public Personphone savePersonphone(Personphone personphone) {
		HttpEntity<Personphone> request = new HttpEntity<>(personphone);
        return restTemplate.postForObject(PHONE_URL, request, Personphone.class);
	}

	@Override
	public void editPersonphone(Personphone personphone) {
		restTemplate.put(PHONE_URL, personphone, Personphone.class);
	}

	
	

	
	
}
