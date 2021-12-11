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
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;

@RestController
@RequestMapping("/api/benphone")
public class PersonphoneRestController {

	@Autowired
	PersonPhoneDao personphoneDao;
	
	@GetMapping
    public Iterable<Personphone> getPersonphones() {
        return personphoneDao.getAll();
    }
	
   @PostMapping
    public void addPersonphone(@RequestBody Personphone personphone) {
        personphoneDao.save(personphone);
    }
	
	@PutMapping
    public void updatePersonphone(@RequestBody Personphone personphone) {
		personphoneDao.update(personphone);
    }
	
	@GetMapping("/{id1}&{id2}")
    public Personphone getById(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2) {
		PersonphonePK id = new PersonphonePK();
		id.setBusinessentityid(id1); id.setPhonenumbertypeid(id2); id.setPhonenumber("3291057293");
        return personphoneDao.get(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id1}&{id2}")
    public void delete(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2) {
		PersonphonePK id = new PersonphonePK();
		id.setBusinessentityid(id1); id.setPhonenumbertypeid(id2); id.setPhonenumber("3291057293");
        personphoneDao.deleteById(id);
    }
	
	@GetMapping("/search/findAllByPerson")
    public List<Personphone> getAllByStateProvince(@RequestParam("person") Integer person) {
        return personphoneDao.findAllByPerson(person);
    }
	
	@GetMapping("/search/findAllByPhoneNumberType")
    public List<Personphone> getAllByModifieddate(@RequestParam("phonenumbertype") Integer phonenumbertype) {
        return personphoneDao.findAllByPhonenumbertype(phonenumbertype);
    }
}
