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

import com.example.demo.back.daos.AddressDao;
import com.example.demo.front.model.person.Address;

@RestController
@RequestMapping("/api/addr")
public class AddressRestController {

	@Autowired
	AddressDao addressDao;
	
	@GetMapping
    public Iterable<Address> getAddresses() {
        return addressDao.getAll();
    }
	
   @PostMapping
    public void addAddress(@RequestBody Address addr) {
        addressDao.save(addr);
    }
	
	@PutMapping
    public void updateAddress(@RequestBody Address addr) {
        addressDao.update(addr);
    }
	
	@GetMapping("/{id}")
    public Address getById(@PathVariable("id") Integer id) {
        return addressDao.get(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
    }
	
	@DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        addressDao.deleteById(id);
    }
	
	@GetMapping("/search/findAllByStateProvince")
    public List<Address> getAllByStateProvince(@RequestParam("stateprovince") Integer stateprovince) {
        return addressDao.findAllByStateprovince(stateprovince);
    }
	
	@GetMapping("/search/findAllByModifieddate")
    public List<Address> getAllByModifieddate(@RequestParam("modifieddate") String modifieddate) {
        return addressDao.findAllByModifieddate(Timestamp.valueOf(modifieddate));
    }

	
}
