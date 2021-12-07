package com.example.demo.controller.implementation;

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

import com.example.demo.controller.interfaces.AddressTypeController;
import com.example.demo.model.person.Addresstype;
import com.example.demo.model.person.Stateprovince;
import com.example.demo.service.interfaces.AddressTypeService;
import com.example.demo.service.interfaces.StateProvinceService;

@Controller
public class AddressTypeControllerImpl implements AddressTypeController{

	private AddressTypeService addrtypeService;
	
	@Autowired
	public AddressTypeControllerImpl(AddressTypeService addrtypeService) {
		this.addrtypeService = addrtypeService;
	}

	@Override
	@GetMapping("/addrtype/")
	public String indexAddressType(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("addrtypes", addrtypeService.findAll());
		}else {
			List<Addresstype> addrtypes = new ArrayList<>();
			addrtypes.add(addrtypeService.findById(id).get());
			model.addAttribute("addrtypes", addrtypes);
		}
		return "addrtype/index";
	}

	@Override
	@GetMapping("/addrtype/add")
	public String addAddressType(Model model) {
		model.addAttribute("addrtype", new Addresstype());
		return "addrtype/add-addrtype";
	}

	@Override
	@PostMapping("/addrtype/add")
	public String saveAddressType(@ModelAttribute("addrtype") @Validated Addresstype addrtype, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("addrtype", addrtype);
				return "addrtype/add-addrtype";
			}
			addrtypeService.saveAddressType(addrtype);
		}
		return "redirect:/addrtype/";
	}

	@Override
	@GetMapping("/addrtype/del/{id}")
	public String deleteAddressType(@PathVariable("id") long id, Model model) {
		Addresstype addrtype = addrtypeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		addrtypeService.delete(addrtype);
		model.addAttribute("addrtypes", addrtypeService.findAll());
		return "addrtype/index";
	}

	@Override
	@GetMapping("/addrtype/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Addresstype addrtype = addrtypeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("addrtype", addrtype);
		return "addrtype/update-addrtype";
	}

	@Override
	@PostMapping("/addrtype/edit/{id}")
	public String updateAddressType(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("addrtype") @Validated Addresstype addrtype, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("addrtype", addrtype);
				return "addrtype/update-addrtype";		
			}
			addrtypeService.updateAddressType(addrtype);
			model.addAttribute("addrtypes", addrtypeService.findAll());
		}
		return "redirect:/addrtype/";
	}
	
}
