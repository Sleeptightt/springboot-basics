package com.example.demo.back.restcontroller;

import java.sql.Timestamp;
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

import com.example.demo.back.daos.PersonPhoneDao;
import com.example.demo.back.service.interfaces.PersonPhoneService;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;

@RestController
@RequestMapping("/api/benphone")
public class PersonphoneRestController {

	@Autowired
	PersonPhoneService personphoneService;
	
	@GetMapping
    public Iterable<Personphone> getPersonphones() {
        return personphoneService.findAll();
    }
	
   @PostMapping
    public void addPersonphone(@RequestBody Personphone personphone) {
	   personphoneService.savePersonPhone(personphone);
    }
	
	@PutMapping
    public void updatePersonphone(@RequestBody Personphone personphone) {
		personphoneService.updatePersonPhone(personphone);
    }
	
	@GetMapping("/{id1}&{id2}")
    public Personphone getById(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2) {
		PersonphonePK id = new PersonphonePK();
		id.setBusinessentityid(id1); id.setPhonenumbertypeid(id2); id.setPhonenumber("3291057293");
        return personphoneService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id1}&{id2}")
    public void delete(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2) {
		PersonphonePK id = new PersonphonePK();
		id.setBusinessentityid(id1); id.setPhonenumbertypeid(id2); id.setPhonenumber("3291057293");
		personphoneService.delete(id);
    }
	
	@GetMapping("/search/findAllByPerson")
    public List<Personphone> getAllByPerson(@RequestParam("person") Integer person) {
        return (List<Personphone>) personphoneService.findAllByPerson(person);
    }
	
	@GetMapping("/search/findAllByPhoneNumberType")
    public List<Personphone> getAllByPhonenumbertype(@RequestParam("phonenumbertype") Integer phonenumbertype) {
        return (List<Personphone>) personphoneService.findAllByPhoneNumbertype(phonenumbertype);
    }
}
