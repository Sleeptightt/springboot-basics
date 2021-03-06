package com.example.demo.back.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.back.daos.PersonDao;
import com.example.demo.back.daos.PersonPhoneDao;
import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;
import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.back.repository.BusinessEntityRepository;
import com.example.demo.back.repository.PhoneNumberTypeRepository;
import com.example.demo.back.service.interfaces.PersonPhoneService;

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

	public Personphone savePersonPhone(Personphone personPhone) {
		
		if(personPhone == null || personPhone.getId() == null 
				|| personPhone.getPerson() == null || personPhone.getPerson().getBusinessentityid() == null 
				|| personPhone.getPhonenumbertype() == null || personPhone.getPhonenumbertype().getPhonenumbertypeid() == null )
		return null;
		
		Integer businessEntityid = personPhone.getPerson().getBusinessentityid();
		Integer phoneNumberTypeid = personPhone.getPhonenumbertype().getPhonenumbertypeid();

		if(!personDao.findById(businessEntityid).isPresent() || !businessEntityRepository.existsById(businessEntityid) || !phoneNumberTypeRepository.existsById(phoneNumberTypeid))
			return null;
		personPhoneDao.save(personPhone);
		return personPhone;
	}

	@Transactional
	public Personphone updatePersonPhone(Personphone personPhone) {
		if(personPhone == null || personPhone.getId() == null 
				|| personPhone.getPerson() == null || personPhone.getPerson().getBusinessentityid() == null 
				|| personPhone.getPhonenumbertype() == null || personPhone.getPhonenumbertype().getPhonenumbertypeid() == null )
		return null;
		Integer businessEntityid = personPhone.getPerson().getBusinessentity().getBusinessentityid();
		Integer phoneNumberTypeid = personPhone.getPhonenumbertype().getPhonenumbertypeid();
		
		if(!personDao.findById(businessEntityid).isPresent() || !businessEntityRepository.existsById(businessEntityid) || !phoneNumberTypeRepository.existsById(phoneNumberTypeid))
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
	public void delete(PersonphonePK id) {
		personPhoneDao.deleteById(id);
	}

	@Override
	public Iterable<Personphone> findAllByPerson(Integer personid) {
		return personPhoneDao.findAllByPerson(personid);
	}

	@Override
	public Iterable<Personphone> findAllByPhoneNumbertype(Integer phonenumbertypeid) {
		return personPhoneDao.findAllByPhonenumbertype(phonenumbertypeid);
	}

}
