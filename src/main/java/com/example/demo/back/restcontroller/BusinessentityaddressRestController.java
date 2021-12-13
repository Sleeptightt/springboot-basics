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

import com.example.demo.back.service.interfaces.BusinessEntityAddressService;
import com.example.demo.back.service.interfaces.PersonPhoneService;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.person.BusinessentityaddressPK;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;

@RestController
@RequestMapping("/api/benaddr")
public class BusinessentityaddressRestController {

	@Autowired
	BusinessEntityAddressService benaddrService;
	
	@GetMapping
    public Iterable<Businessentityaddress> getBusinessentityaddress() {
        return benaddrService.findAll();
    }
	
   @PostMapping
    public void addBusinessentityaddress(@RequestBody Businessentityaddress benaddr) {
	   benaddrService.saveBusinessEntityAddress(benaddr);
    }
	
	@PutMapping
    public void updateBusinessentityaddress(@RequestBody Businessentityaddress benaddr) {
		benaddrService.updateBusinessEntityAddress(benaddr);
    }
	
	@GetMapping("/{id1}&{id2}&{id3}")
    public Businessentityaddress getById(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2, @PathVariable("id3") Integer id3) {
		BusinessentityaddressPK id = new BusinessentityaddressPK();
		id.setAddressid((int) id1); id.setAddresstypeid((int) id2); id.setBusinessentityid((int) id3);
		
        return benaddrService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id1}&{id2}&{id3}")
    public void delete(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2, @PathVariable("id3") Integer id3) {
		BusinessentityaddressPK id = new BusinessentityaddressPK();
		id.setAddressid((int) id1); id.setAddresstypeid((int) id2); id.setBusinessentityid((int) id3);
		benaddrService.delete(id);
    }
	
	@GetMapping("/search/findAllByAddress")
    public List<Businessentityaddress> getAllByAddress(@RequestParam("address") Integer address) {
        return (List<Businessentityaddress>) benaddrService.findAllByAddress(address);
    }
	
	@GetMapping("/search/findAllByAddresstype")
    public List<Businessentityaddress> getAllByAddresstype(@RequestParam("addresstype") Integer addresstype) {
        return (List<Businessentityaddress>) benaddrService.findAllByAddresstype(addresstype);
    }
	
	@GetMapping("/search/findAllByBusinessentity")
    public List<Businessentityaddress> getAllByBusinessentity(@RequestParam("businessentity") Integer businessentity) {
        return (List<Businessentityaddress>) benaddrService.findAllByBusinessentity(businessentity);
    }
}
