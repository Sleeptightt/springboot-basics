package com.example.demo.front.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.front.model.person.Address;

public interface AddressController {
	
	public String indexAddress(@RequestParam(required = false, value = "id") Long id, Model model);
	public String addAddress(Model model);
	public String saveAddress(@ModelAttribute("addr") @Validated Address addr, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deleteAddress(@PathVariable("id") long id, Model model);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updateAddress(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("addr") @Validated Address addr, BindingResult bindingResult, Model model);
}
