package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.daos.PersonDao;
import com.example.demo.daos.PersonPhoneDao;
import com.example.demo.model.person.Address;
import com.example.demo.model.person.Businessentityaddress;
import com.example.demo.model.person.BusinessentityaddressPK;
import com.example.demo.model.person.Person;
import com.example.demo.model.person.Personphone;
import com.example.demo.model.person.PersonphonePK;
import com.example.demo.model.person.Phonenumbertype;
import com.example.demo.repository.AddressTypeRepository;
import com.example.demo.repository.BusinessEntityRepository;
import com.example.demo.repository.PhoneNumberTypeRepository;
import com.example.demo.service.interfaces.PersonPhoneService;

import lombok.extern.java.Log;

@Log
@Service
public class PersonPhoneServiceImpl implements PersonPhoneService{

	private PersonPhoneDao personPhoneDao;
	
	private PersonDao personDao;
	
	private PhoneNumberTypeRepository phoneNumberTypeRepository;
	
	private BusinessEntityRepository businessEntityRepository;
	
	@Autowired
	public PersonPhoneServiceImpl(PersonPhoneDao personPhoneDao, PersonDao personDao,
			PhoneNumberTypeRepository phoneNumberTypeRepository, BusinessEntityRepository businessEntityRepository) {
		this.personPhoneDao = personPhoneDao;
		this.personDao = personDao;
		this.phoneNumberTypeRepository = phoneNumberTypeRepository;
		this.businessEntityRepository = businessEntityRepository;
	}

	@Transactional
	public Personphone savePersonPhone(Personphone personPhone) {
		
		if(personPhone == null || personPhone.getId() == null 
				|| personPhone.getPerson() == null || personPhone.getPerson().getBusinessentityid() == null 
				|| personPhone.getPhonenumbertype() == null || personPhone.getPhonenumbertype().getPhonenumbertypeid() == null 
				|| personPhone.getPerson().getBusinessentity() == null || personPhone.getPerson().getBusinessentity().getBusinessentityid() == null)
		return null;
		
		Integer personid = personPhone.getPerson().getBusinessentityid();
		Integer businessEntityid = personPhone.getPerson().getBusinessentity().getBusinessentityid();
		Integer phoneNumberTypeid = personPhone.getPhonenumbertype().getPhonenumbertypeid();
		
		if(!personDao.findById(personid).isPresent() || !businessEntityRepository.existsById(businessEntityid) || !phoneNumberTypeRepository.existsById(phoneNumberTypeid))
			return null;
		personPhoneDao.save(personPhone);
		return personPhone;
	}

	@Transactional
	public Personphone updatePersonPhone(Personphone personPhone) {
		if(personPhone == null || personPhone.getId() == null 
				|| personPhone.getPerson() == null || personPhone.getPerson().getBusinessentityid() == null 
				|| personPhone.getPhonenumbertype() == null || personPhone.getPhonenumbertype().getPhonenumbertypeid() == null 
				|| personPhone.getPerson().getBusinessentity() == null || personPhone.getPerson().getBusinessentity().getBusinessentityid() == null)
		return null;
		Integer personid = personPhone.getPerson().getBusinessentityid();
		Integer businessEntityid = personPhone.getPerson().getBusinessentity().getBusinessentityid();
		Integer phoneNumberTypeid = personPhone.getPhonenumbertype().getPhonenumbertypeid();
		
		if(!personDao.findById(personid).isPresent() || !businessEntityRepository.existsById(businessEntityid) || !phoneNumberTypeRepository.existsById(phoneNumberTypeid))
			return null;
		PersonphonePK id = personPhone.getId();
		Personphone existingPhone = personPhoneDao.findById(id).get();
		existingPhone.setId(personPhone.getId());
		existingPhone.setModifieddate(personPhone.getModifieddate());
		existingPhone.setPerson(personPhone.getPerson());
		existingPhone.setPhonenumbertype(personPhone.getPhonenumbertype());
		return personPhone;
	}

	@Override
	public Iterable<Personphone> findAll() {
		return personPhoneDao.getAll();
	}

	@Override
	public Optional<Personphone> findById(PersonphonePK id) {
		return personPhoneDao.findById(id);
	}

	@Override
	public void delete(Personphone benphone) {
		personPhoneDao.deleteById(benphone.getId());
	}

	@Override
	public Iterable<Personphone> findAllByPerson(Person person) {
		return personPhoneDao.findAllByPerson(person);
	}

	@Override
	public Iterable<Personphone> findAllByPhoneNumbertype(Phonenumbertype phonetype) {
		return personPhoneDao.findAllByPhonenumbertype(phonetype);
	}

}
