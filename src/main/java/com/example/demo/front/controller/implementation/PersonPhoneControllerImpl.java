package com.example.demo.front.controller.implementation;

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

import com.example.demo.front.controller.interfaces.PersonPhoneController;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;
import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.back.service.interfaces.PersonPhoneService;
import com.example.demo.back.service.interfaces.PersonService;
import com.example.demo.back.service.interfaces.PhoneNumberTypeService;

import lombok.extern.java.Log;

@Log
@Controller
public class PersonPhoneControllerImpl implements PersonPhoneController{
	
	private PersonPhoneService benphoneService;
	private PersonService personService;
	private PhoneNumberTypeService phonetypeService;
	
	@Autowired
	public PersonPhoneControllerImpl(PersonPhoneService benphoneService, PersonService personService,
			PhoneNumberTypeService phonetypeService) {
		this.benphoneService = benphoneService;
		this.personService = personService;
		this.phonetypeService = phonetypeService;
	}

	@Override
	@GetMapping("/benphone/")
	public String indexPersonPhone(@RequestParam(required = false, value = "id1") Long id1, @RequestParam(required = false, value = "id2") Long id2, Model model) {
		if(id1 != null) {
			Person person = personService.findById(id1).get();
			model.addAttribute("benphones", benphoneService.findAllByPerson(person));
		}else if(id2 != null) {
			Phonenumbertype phonetype = phonetypeService.findById(id2).get();
			model.addAttribute("benphones", benphoneService.findAllByPhoneNumbertype(phonetype));
		}else {
			model.addAttribute("benphones", benphoneService.findAll());
		}
		return "benphone/index";
	}

	@Override
	@GetMapping("/benphone/add")
	public String addPersonPhone(Model model) {
		model.addAttribute("benphone", new Personphone());
		model.addAttribute("persons", personService.findAll());
		model.addAttribute("phonetypes", phonetypeService.findAll());
		return "benphone/add-benphone";
	}

	@Override
	@PostMapping("/benphone/add")
	public String savePersonPhone(@ModelAttribute("benphone") @Validated Personphone benphone, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("benphone", new Personphone());
				model.addAttribute("persons", personService.findAll());
				model.addAttribute("phonetypes", phonetypeService.findAll());
				log.info(result.getAllErrors().toString() + " " + benphone.getPhonenumbertype().toString());
				return "benphone/add-benphone";
			}
			PersonphonePK id = new PersonphonePK();
			id.setBusinessentityid((int) benphone.getPerson().getBusinessentityid()); id.setPhonenumbertypeid((int) benphone.getPhonenumbertype().getPhonenumbertypeid()); 
			id.setPhonenumber("3291057293");
			benphone.setId(id);
			benphoneService.savePersonPhone(benphone);
		}
		return "redirect:/benphone/";
	}

	@Override
	@GetMapping("/benphone/del/{id1}&{id2}")
	public String deletePersonPhone(@PathVariable("id1") long id1, @PathVariable("id2") long id2, Model model) {
		PersonphonePK id = new PersonphonePK();
		id.setBusinessentityid((int) id1); id.setPhonenumbertypeid((int) id2); id.setPhonenumber("3291057293");
		Personphone benphone = benphoneService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		benphoneService.delete(benphone);
		model.addAttribute("benphones", benphoneService.findAll());
		return "benphone/index";
	}

	@Override
	@GetMapping("/benphone/edit/{id1}&{id2}")
	public String showUpdateForm(@PathVariable("id1") long id1, @PathVariable("id2") long id2, Model model) {
		PersonphonePK id = new PersonphonePK();
		id.setBusinessentityid((int) id1); id.setPhonenumbertypeid((int) id2); id.setPhonenumber("3291057293");
		Personphone benphone = benphoneService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("benphone", benphone);
		model.addAttribute("persons", personService.findAll());
		model.addAttribute("phonetypes", phonetypeService.findAll());
		return "benphone/update-benphone";
	}

	@Override
	@PostMapping("/benphone/edit/{id1}&{id2}")
	public String updatePersonPhone(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("benphone") @Validated Personphone benphone, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("benphone", new Personphone());
				model.addAttribute("persons", personService.findAll());
				model.addAttribute("phonetypes", phonetypeService.findAll());
				return "benphone/update-benphone";
			}
			PersonphonePK id = new PersonphonePK();
			id.setBusinessentityid((int) benphone.getPerson().getBusinessentityid()); id.setPhonenumbertypeid((int) benphone.getPhonenumbertype().getPhonenumbertypeid()); 
			id.setPhonenumber("3291057293");
			benphone.setId(id);
			benphoneService.updatePersonPhone(benphone);
			model.addAttribute("benphones", benphoneService.findAll());
		}
		return "redirect:/benphone/";
	}
	
}
