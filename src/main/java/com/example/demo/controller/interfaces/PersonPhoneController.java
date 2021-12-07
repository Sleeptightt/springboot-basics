package com.example.demo.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.person.Personphone;

public interface PersonPhoneController {
	
	public String indexPersonPhone(@RequestParam(required = false, value = "id1") Long id1, @RequestParam(required = false, value = "id2") Long id2, Model model);
	public String addPersonPhone(Model model);
	public String savePersonPhone(@ModelAttribute("benphone") @Validated Personphone benphone, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deletePersonPhone(@PathVariable("id1") long id1, @PathVariable("id2") long id2, Model model);
	public String showUpdateForm(@PathVariable("id1") long id1, @PathVariable("id2") long id2, Model model);
	public String updatePersonPhone(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("benphone") @Validated Personphone benphone, BindingResult bindingResult, Model model);
	
}
