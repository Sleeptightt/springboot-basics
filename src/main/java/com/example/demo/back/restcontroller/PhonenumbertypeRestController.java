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

import com.example.demo.back.service.interfaces.PhoneNumberTypeService;
import com.example.demo.back.service.interfaces.StateProvinceService;
import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.front.model.person.Stateprovince;

@RestController
@RequestMapping("/api/phonetype")
public class PhonenumbertypeRestController {

	@Autowired
	private PhoneNumberTypeService phoneService;
	
	@GetMapping
    public Iterable<Phonenumbertype> getPhonenumbertypes() {
        return phoneService.findAll();
    }
	
    @PostMapping
    public void addPhonenumbertype(@RequestBody Phonenumbertype phone) {
    	phoneService.savePhoneNumberType(phone);
    }
	
	@PutMapping
    public void updatePhonenumbertype(@RequestBody Phonenumbertype phone) {
		phoneService.updatePhoneNumberType(phone);
    }
	
	@GetMapping("/{id}")
    public Phonenumbertype getById(@PathVariable("id") Integer id) {
        return phoneService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        phoneService.delete(id);
    }
}
