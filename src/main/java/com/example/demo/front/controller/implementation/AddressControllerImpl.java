package com.example.demo.front.controller.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.front.businessdelegate.interfaces.BusinessDelegate;
import com.example.demo.front.controller.interfaces.AddressController;
import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.back.service.interfaces.AddressService;
import com.example.demo.back.service.interfaces.StateProvinceService;

import lombok.extern.java.Log;

@Log
@Controller
public class AddressControllerImpl implements AddressController{
	
	@Autowired
	private BusinessDelegate businessDelegate;
	
	public AddressControllerImpl() {

	}

	@GetMapping("/addr/")
	public String indexAddress(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("addrs", businessDelegate.findAllAddresses());
		}else {
			Stateprovince stprov = businessDelegate.findStateprovinceById(id.intValue());
			model.addAttribute("addrs", businessDelegate.findAllAddressByStateProvince(stprov.getStateprovinceid()));
		}
		return "addr/index";
	}
	
	@GetMapping("/addr/add")
	public String addAddress(Model model) {
		model.addAttribute("addr", new Address());
		model.addAttribute("stprovs", businessDelegate.findAllStateprovinces());
		return "addr/add-addr";
	}
	
	@PostMapping("/addr/add")
	public String saveAddress(@ModelAttribute("addr") @Validated Address address, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("addr", address);
				model.addAttribute("stprovs", businessDelegate.findAllStateprovinces());
				return "addr/add-addr";
			}
			businessDelegate.saveAddress(address);
		}
		return "redirect:/addr/";
	}
	
	@GetMapping("/addr/del/{id}")
	public String deleteAddress(@PathVariable("id") long id, Model model) {
		Address addr = businessDelegate.findAddressById((int)id);
		businessDelegate.deleteAddress(addr);
		model.addAttribute("addrs", businessDelegate.findAllAddresses());
		return "addr/index";
	}
	
	@GetMapping("/addr/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Address addr = businessDelegate.findAddressById((int)id);
		model.addAttribute("addr", addr);
		model.addAttribute("stprovs", businessDelegate.findAllStateprovinces());
		return "addr/update-addr";
	}
	
	@PostMapping("/addr/edit/{id}")
	public String updateAddress(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("addr") @Validated Address address, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("addr", address);
				model.addAttribute("stprovs", businessDelegate.findAllStateprovinces());
				return "addr/update-addr";		
			}
			businessDelegate.editAddress(address);
			model.addAttribute("addrs", businessDelegate.findAllAddresses());
		}
		return "redirect:/addr/";
	}
	
}
