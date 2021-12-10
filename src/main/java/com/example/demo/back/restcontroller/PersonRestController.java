package com.example.demo.back.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.back.daos.PersonDao;
import com.example.demo.front.model.person.Person;

@RestController
@RequestMapping("/api/person")
public class PersonRestController {
	
	@Autowired
	PersonDao personDao;
	
	@GetMapping
    public Iterable<Person> getPersons() {
        return personDao.getAll();
    }
	
   @PostMapping
    public void addPerson(@RequestBody Person person) {
        personDao.save(person);
    }
	
	@PutMapping
    public void updatePerson(@RequestBody Person person) {
        personDao.update(person);
    }
	
	@GetMapping("/{id}")
    public Person getById(@PathVariable("id") Integer id) {
        return personDao.get(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        personDao.deleteById(id);
    }
	
	@GetMapping("/search/findAllByPersonType")
    public List<Person> getAllByPersonType(@RequestParam("persontype") String persontype) {
        return personDao.findAllByPersontype(persontype);
    }
	
	@GetMapping("/search/findAllByTitle")
    public List<Person> getAllByTitle(@RequestParam("title") String title) {
        return personDao.findAllByTitle(title);
    }
	
	@GetMapping("/search/findAllByBusinessEntity")
    public List<Person> getAllByTitle(@RequestParam("businessentity") Integer businessentity) {
        return personDao.findAllByBusinessentityid(businessentity);
    }
}
