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
import com.example.demo.front.controller.interfaces.PersonController;
import com.example.demo.front.model.person.Person;
import com.example.demo.back.service.interfaces.BusinessEntityService;
import com.example.demo.back.service.interfaces.PersonService;

@Controller
public class PersonControllerImpl implements PersonController{
	
	@Autowired
	private BusinessDelegate businessDelegate;
	
	public PersonControllerImpl() {
	}
	
	@Override
	@GetMapping("/person/")
	public String indexPerson(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("persons", businessDelegate.findAllPersons());
		}else {
			List<Person> persons = new ArrayList<>();
			persons.add(businessDelegate.findPersonById(id.intValue()));
			model.addAttribute("persons", persons);
		}
		return "person/index";
	}

	@Override
	@GetMapping("/person/add")
	public String addPerson(Model model) {
		model.addAttribute("person", new Person());
		model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		return "person/add-person";
	}

	@Override
	@PostMapping("/person/add")
	public String savePerson(@ModelAttribute @Validated Person person, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("person", person);
				model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
				return "person/add-person";
			}
			businessDelegate.savePerson(person);
		}
		return "redirect:/person/";
	}

	@Override
	@GetMapping("/person/del/{id}")
	public String deletePerson(@PathVariable("id") long id, Model model) {
		Person person = businessDelegate.findPersonById((int) id);
		businessDelegate.deletePerson(person);
		model.addAttribute("persons", businessDelegate.findAllPersons());
		model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		return "person/index";
	}

	@Override
	@GetMapping("/person/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Person person = businessDelegate.findPersonById((int) id);
		model.addAttribute("person", person);
		model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		return "person/update-person";
	}

	@Override
	@PostMapping("/person/edit/{id}")
	public String updatePerson(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action, @ModelAttribute @Validated Person person, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("person", person);
				model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
				return "person/update-person";		
			}
			businessDelegate.editPerson(person);
			model.addAttribute("persons", businessDelegate.findAllPersons());
		}
		return "redirect:/person/";
	}

}
