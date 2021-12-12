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
import com.example.demo.front.controller.interfaces.PhoneNumberTypeController;
import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.back.service.interfaces.PhoneNumberTypeService;

@Controller
public class PhoneNumberTypeControllerImpl implements PhoneNumberTypeController{
	
	@Autowired
	private BusinessDelegate businessDelegate;
	
	@Autowired
	public PhoneNumberTypeControllerImpl() {
	}

	@Override
	@GetMapping("/phonetype/")
	public String indexPhoneNumberType(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("phonetypes", businessDelegate.findAllPhonenumbertypes());
		}else {
			List<Phonenumbertype> phonetypes = new ArrayList<>();
			phonetypes.add(businessDelegate.findPhonenumbertypeById(id.intValue()));
			model.addAttribute("phonetypes", phonetypes);
		}
		return "phonetype/index";
	}

	@Override
	@GetMapping("/phonetype/add")
	public String addPhoneNumberType(Model model) {
		model.addAttribute("phonetype", new Phonenumbertype());
		return "phonetype/add-phonetype";
	}

	@Override
	@PostMapping("/phonetype/add")
	public String savePhoneNumberType(@ModelAttribute("phonetype") @Validated Phonenumbertype phonetype, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("phonetype", phonetype);
				return "phonetype/add-phonetype";
			}
			businessDelegate.savePhonenumbertype(phonetype);
		}
		return "redirect:/phonetype/";
	}

	@Override
	@GetMapping("/phonetype/del/{id}")
	public String deletePhoneNumberType(@PathVariable("id") long id, Model model) {
		Phonenumbertype phonetype = businessDelegate.findPhonenumbertypeById((int)id);
		businessDelegate.deletePhonenumbertype(phonetype);
		model.addAttribute("phonetypes", businessDelegate.findAllPhonenumbertypes());
		return "phonetype/index";
	}

	@Override
	@GetMapping("/phonetype/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Phonenumbertype phonetype = businessDelegate.findPhonenumbertypeById((int)id);
		model.addAttribute("phonetype", phonetype);
		return "phonetype/update-phonetype";
	}

	@Override
	@PostMapping("/phonetype/edit/{id}")
	public String updatePhoneNumberType(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("phonetype") @Validated Phonenumbertype phonetype, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("phonetype", phonetype);
				return "phonetype/update-phonetype";		
			}
			businessDelegate.editPhonenumbertype(phonetype);
			model.addAttribute("phonetypes", businessDelegate.findAllPhonenumbertypes());
		}
		return "redirect:/phonetype/";
	}
	
}
