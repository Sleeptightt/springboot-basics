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

import com.example.demo.back.service.interfaces.AddressTypeService;
import com.example.demo.back.service.interfaces.StateProvinceService;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Stateprovince;

@RestController
@RequestMapping("/api/addrtype")
public class AddresstypeRestController {

	@Autowired
	private AddressTypeService addrtypeService;
	
	@GetMapping
    public Iterable<Addresstype> getAddresstypes() {
        return addrtypeService.findAll();
    }
	
    @PostMapping
    public void addAddresstype(@RequestBody Addresstype addrtype) {
    	addrtypeService.saveAddressType(addrtype);
    }
	
	@PutMapping
    public void updateAddresstype(@RequestBody Addresstype addrtype) {
		addrtypeService.updateAddressType(addrtype);
    }
	
	@GetMapping("/{id}")
    public Addresstype getById(@PathVariable("id") Integer id) {
        return addrtypeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        addrtypeService.delete(id);
    }
}
