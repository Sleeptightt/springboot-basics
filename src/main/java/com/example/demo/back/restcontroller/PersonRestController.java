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
import com.example.demo.back.repository.BusinessEntityRepository;
import com.example.demo.back.service.interfaces.PersonService;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Person;

@RestController
@RequestMapping("/api/person")
public class PersonRestController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping
    public Iterable<Person> getPersons() {
        return personService.findAll();
    }
	
    @PostMapping
    public void addPerson(@RequestBody Person person) {
	   	personService.savePerson(person);
    }
	
	@PutMapping
    public void updatePerson(@RequestBody Person person) {
        personService.updatePerson(person);
    }
	
	@GetMapping("/{id}")
    public Person getById(@PathVariable("id") Integer id) {
        return personService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        personService.delete(id);
    }
	
	@GetMapping("/search/findAllByPersonType")
    public List<Person> getAllByPersonType(@RequestParam("persontype") String persontype) {
        return personService.findAllByPersontype(persontype);
    }
	
	@GetMapping("/search/findAllByTitle")
    public List<Person> getAllByTitle(@RequestParam("title") String title) {
        return personService.findAllByTitle(title);
    }
	
	@GetMapping("/search/findAllByBusinessEntity")
    public List<Person> getAllByTitle(@RequestParam("businessentity") Integer businessentity) {
        return personService.findAllByBusinessentityid(businessentity);
    }
}
