package com.example.demo.front.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.front.model.person.Person;

public interface PersonController {
	public String indexPerson(@RequestParam(required = false, value = "id") Long id, Model model);
	public String addPerson(Model model);
	public String savePerson(@ModelAttribute("person") @Validated Person person, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deletePerson(@PathVariable("id") long id, Model model);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updatePerson(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("person") @Validated Person person, BindingResult bindingResult, Model model);
	
}
