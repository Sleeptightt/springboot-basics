package com.example.demo.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.person.Addresstype;
import com.example.demo.model.person.Phonenumbertype;

public interface PhoneNumberTypeController {

	public String indexPhoneNumberType(@RequestParam(required = false, value = "id") Long id, Model model);
	public String addPhoneNumberType(Model model);
	public String savePhoneNumberType(@ModelAttribute("phonetype") @Validated Phonenumbertype phonetype, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deletePhoneNumberType(@PathVariable("id") long id, Model model);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updatePhoneNumberType(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("phonetype") @Validated Phonenumbertype phonetype, BindingResult bindingResult, Model model);
	
	
}
