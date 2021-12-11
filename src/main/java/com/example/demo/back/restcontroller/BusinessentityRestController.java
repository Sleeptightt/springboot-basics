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

import com.example.demo.back.service.interfaces.BusinessEntityService;
import com.example.demo.back.service.interfaces.PersonService;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Person;

@RestController
@RequestMapping("/api/ben")
public class BusinessentityRestController {

	@Autowired
	private BusinessEntityService benService;
	
	@GetMapping
    public Iterable<Businessentity> getBusinessEntities() {
        return benService.findAll();
    }
	
    @PostMapping
    public void addBusinessentity(@RequestBody Businessentity ben) {
    	benService.saveBusinessEntity(ben);
    }
	
	@PutMapping
    public void updateBusinessentity(@RequestBody Businessentity ben) {
		benService.updateBusinessEntity(ben);
    }
	
	@GetMapping("/{id}")
    public Businessentity getById(@PathVariable("id") Integer id) {
        return benService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        benService.delete(id);
    }
	
}
