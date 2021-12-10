package com.example.demo.front.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.front.model.person.Addresstype;

public interface AddressTypeController {

	public String indexAddressType(@RequestParam(required = false, value = "id") Long id, Model model);
	public String addAddressType(Model model);
	public String saveAddressType(@ModelAttribute("addrtype") @Validated Addresstype addrtype, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deleteAddressType(@PathVariable("id") long id, Model model);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updateAddressType(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("addrtype") @Validated Addresstype addrtype, BindingResult bindingResult, Model model);
	
}
