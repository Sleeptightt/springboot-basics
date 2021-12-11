package com.example.demo.back.service;

import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.back.daos.PersonDao;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Person;
import com.example.demo.back.repository.BusinessEntityRepository;
import com.example.demo.back.service.interfaces.PersonService;

import lombok.extern.java.Log;

@Log
@Service
public class PersonServiceImpl implements PersonService{

	private PersonDao personDao;
	private BusinessEntityRepository benRepository;
	
	@Autowired
	public PersonServiceImpl(PersonDao personDao, BusinessEntityRepository benRepository) {
		this.personDao = personDao;
		this.benRepository = benRepository;
	}
	
	public Person savePerson(Person person) {
		if(person == null) {
			return null;
		}
		boolean cond = person.getFirstname() != null && person.getFirstname().length()>=3 && person.getMiddlename() != null 
				&& person.getMiddlename().length()>=3 && person.getLastname()!=null && person.getLastname().length()>=3;
		if(!cond) return null;
		Businessentity ben = new Businessentity();
		ben = benRepository.save(ben);
		person.setBusinessentity(ben);
		personDao.save(person);
		return person;
	}
	
	@Transactional
	public Person updatePerson(Person person) {
		if(person == null || !personDao.findById(person.getBusinessentityid()).isPresent()) {
			return null;
		}
		Integer bid = person.getBusinessentityid();
		Person existingPerson = personDao.findById(bid).get();
		boolean cond = person.getFirstname() != null && person.getFirstname().length()>=3 && person.getMiddlename() != null 
				&& person.getMiddlename().length()>=3 && person.getLastname()!=null && person.getLastname().length()>=3;
		if(!cond) return null;
		existingPerson.setFirstname(person.getFirstname());
		existingPerson.setMiddlename(person.getMiddlename());
		existingPerson.setLastname(person.getLastname());
		existingPerson.setFirstname(person.getFirstname());
		existingPerson.setTitle(person.getTitle());
		existingPerson.setAdditionalcontactinfo(person.getAdditionalcontactinfo());
		return person;
	}

	public boolean existsById(Integer personid) {
		return personDao.findById(personid).isPresent();
	}

	@Override
	public Iterable<Person> findAll() {
		return personDao.getAll();
	}

	@Override
	public Optional<Person> findById(long id) {
		return personDao.findById((int) id);
	}

	@Override
	public void delete(Integer id) {
		personDao.deleteById(id);
	}
	
	@Override
    public List<Person> findAllByPersontype(String persontype) {
        return personDao.findAllByPersontype(persontype);
    }
	
	@Override
    public List<Person> findAllByTitle(String title) {
        return personDao.findAllByTitle(title);
    }
	
	@Override
    public List<Person> findAllByBusinessentityid(Integer businessentity) {
        return personDao.findAllByBusinessentityid(businessentity);
    }
	
}
