package com.example.demo.back.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.back.service.interfaces.BusinessEntityService;
import com.example.demo.back.service.interfaces.StateProvinceService;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Stateprovince;

@RestController
@RequestMapping("/api/stprov")
public class StateprovinceRestController {

	@Autowired
	private StateProvinceService stprovService;
	
	@GetMapping
    public Iterable<Stateprovince> getStateProvinces() {
        return stprovService.findAll();
    }
	
    @PostMapping
    public void addStateprovince(@RequestBody Stateprovince stprov) {
    	stprovService.saveStateprovince(stprov);
    }
	
	@PutMapping
    public void updateBusinessentity(@RequestBody Stateprovince stprov) {
		stprovService.updateStateprovince(stprov);
    }
	
	@GetMapping("/{id}")
    public Stateprovince getById(@PathVariable("id") Integer id) {
        return stprovService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        stprovService.delete(id);
    }
}
