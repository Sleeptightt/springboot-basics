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
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.person.BusinessentityaddressPK;
import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;
import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.front.model.sales.Store;

@Component
public class BusinessDelegateImpl implements BusinessDelegate{

	private final static String URL = "http://localhost:8080/api";

	private final static String BEN_URL = URL + "/ben/";
	private final static String STPROV_URL = URL + "/stprov/";
	private final static String ADDR_URL = URL + "/addr/";
    private final static String PER_URL = URL + "/person/";
    private final static String PHONE_URL = URL + "/benphone/";
    private final static String PHONE_TYPE_URL = URL + "/phonetype/";
    private final static String ADDR_TYPE_URL = URL + "/addrtype/";
    private final static String BEN_ADDR_URL = URL + "/benaddr/";
    private final static String STORE_URL = URL + "/store/";
	
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

	// Businessentity --------------------------------------------------------------------------------------------
	
	@Override
	public List<Businessentity> findAllBusinessentitys() {
		Businessentity[] array = restTemplate.getForObject(BEN_URL, Businessentity[].class);
		
		return Arrays.asList(array);
	}

	@Override
	public Businessentity findBusinessentityById(Integer id) {
		return restTemplate.getForObject(BEN_URL+id, Businessentity.class);
	}

	@Override
	public void deleteBusinessentity(Businessentity ben) {
		restTemplate.delete(BEN_URL+ben.getBusinessentityid());
	}

	@Override
	public Businessentity saveBusinessentity(Businessentity ben) {
		HttpEntity<Businessentity> request = new HttpEntity<>(ben);
        return restTemplate.postForObject(BEN_URL, request, Businessentity.class);
	}

	@Override
	public void editBusinessentity(Businessentity ben) {
		restTemplate.put(BEN_URL, ben, Businessentity.class);
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

	//Stateprovince --------------------------------------------------------------------------------------------
	
	@Override
	public List<Stateprovince> findAllStateprovinces() {
		Stateprovince[] array = restTemplate.getForObject(STPROV_URL, Stateprovince[].class);
		return Arrays.asList(array);
	}

	@Override
	public Stateprovince findStateprovinceById(Integer id) {
		return restTemplate.getForObject(STPROV_URL+id, Stateprovince.class);
	}

	@Override
	public void deleteStateprovince(Stateprovince stprov) {
		restTemplate.delete(STPROV_URL+stprov.getStateprovinceid());
	}

	@Override
	public Stateprovince saveStateprovince(Stateprovince stprov) {
		HttpEntity<Stateprovince> request = new HttpEntity<>(stprov);
        return restTemplate.postForObject(STPROV_URL, request, Stateprovince.class);
	}

	@Override
	public void editStateprovince(Stateprovince stprov) {
		restTemplate.put(STPROV_URL, stprov, Stateprovince.class);
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

	//Addresstype --------------------------------------------------------------------------------------------
	
	@Override
	public List<Addresstype> findAllAddresstypes() {
		Addresstype [] array = restTemplate.getForObject(ADDR_TYPE_URL, Addresstype[].class);
		return Arrays.asList(array);
	}

	@Override
	public Addresstype findAddresstypeById(Integer id) {
		return restTemplate.getForObject(ADDR_TYPE_URL+id, Addresstype.class);
	}

	@Override
	public void deleteAddresstype(Addresstype addrtype) {
		restTemplate.delete(ADDR_TYPE_URL+addrtype.getAddresstypeid());
	}

	@Override
	public Addresstype saveAddresstype(Addresstype addrtype) {
		HttpEntity<Addresstype> request = new HttpEntity<>(addrtype);
        return restTemplate.postForObject(ADDR_TYPE_URL, request, Addresstype.class);
	}

	@Override
	public void editAddresstype(Addresstype addrtype) {
		restTemplate.put(ADDR_TYPE_URL, addrtype, Addresstype.class);
	}
	
	//Phonenumbertype --------------------------------------------------------------------------------------------
	
	@Override
	public List<Phonenumbertype> findAllPhonenumbertypes() {
		Phonenumbertype[] array = restTemplate.getForObject(PHONE_TYPE_URL, Phonenumbertype[].class);
		
		return Arrays.asList(array);
	}

	@Override
	public Phonenumbertype findPhonenumbertypeById(Integer id) {
		return restTemplate.getForObject(PHONE_TYPE_URL+id, Phonenumbertype.class);
	}

	@Override
	public void deletePhonenumbertype(Phonenumbertype phone) {
		restTemplate.delete(PHONE_TYPE_URL+phone.getPhonenumbertypeid());
	}

	@Override
	public Phonenumbertype savePhonenumbertype(Phonenumbertype phone) {
		HttpEntity<Phonenumbertype> request = new HttpEntity<>(phone);
        return restTemplate.postForObject(PHONE_TYPE_URL, request, Phonenumbertype.class);
	}

	@Override
	public void editPhonenumbertype(Phonenumbertype phone) {
		restTemplate.put(PHONE_TYPE_URL, phone, Phonenumbertype.class);
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

	//Businessentityadress --------------------------------------------------------------------------------------------
	
	@Override
	public List<Businessentityaddress> findAllBusinessentityaddressByAddress(Integer address) {
		Businessentityaddress[] array = restTemplate.getForObject(BEN_ADDR_URL+
                "search/findAllByAddress?address=" + address, Businessentityaddress[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Businessentityaddress> findAllBusinessentityaddressByAddresstype(Integer addresstype) {
		Businessentityaddress[] array = restTemplate.getForObject(BEN_ADDR_URL+
                "search/findAllByAddresstype?addresstype=" + addresstype, Businessentityaddress[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Businessentityaddress> findAllBusinessentityaddressByBusinessentity(Integer businessentity) {
		Businessentityaddress[] array = restTemplate.getForObject(BEN_ADDR_URL+
                "search/findAllByBusinessentity?businessentity=" + businessentity, Businessentityaddress[].class);
        return Arrays.asList(array);
	}

	@Override
	public List<Businessentityaddress> findAllBusinessentityaddress() {
		Businessentityaddress[] array = restTemplate.getForObject(BEN_ADDR_URL, Businessentityaddress[].class);
		
		return Arrays.asList(array);
	}

	@Override
	public Businessentityaddress findBusinessentityaddressById(BusinessentityaddressPK id) {
		return restTemplate.getForObject(BEN_ADDR_URL+id.getAddressid()+"&"+id.getAddresstypeid()+"&"+id.getBusinessentityid(), Businessentityaddress.class);
	}

	@Override
	public void deleteBusinessentityaddress(Businessentityaddress benaddr) {
		restTemplate.delete(BEN_ADDR_URL+benaddr.getId().getAddressid()+"&"+benaddr.getId().getAddresstypeid()+"&"+benaddr.getId().getBusinessentityid());
	}

	@Override
	public Businessentityaddress saveBusinessentityaddress(Businessentityaddress benaddr) {
		HttpEntity<Businessentityaddress> request = new HttpEntity<>(benaddr);
        return restTemplate.postForObject(BEN_ADDR_URL, request, Businessentityaddress.class);
	}

	@Override
	public void editBusinessentityaddress(Businessentityaddress benaddr) {
		restTemplate.put(BEN_ADDR_URL, benaddr, Businessentityaddress.class);
	}

	//Store --------------------------------------------------------------------------------------------
	
	@Override
	public List<Store> findAllStores() {
		Store[] array = restTemplate.getForObject(STORE_URL, Store[].class);
		
		return Arrays.asList(array);
	}

	@Override
	public Store findStoreById(Integer id) {
		return restTemplate.getForObject(STORE_URL+id, Store.class);
	}

	@Override
	public void deleteStore(Store store) {
		restTemplate.delete(STORE_URL+store.getBusinessentityid());
	}

	@Override
	public Store saveStore(Store store) {
		HttpEntity<Store> request = new HttpEntity<>(store);
        return restTemplate.postForObject(STORE_URL, request, Store.class);
	}

	@Override
	public void editStore(Store store) {
		restTemplate.put(STORE_URL, store, Store.class);
	}

	

	



	
	

	
	
}
