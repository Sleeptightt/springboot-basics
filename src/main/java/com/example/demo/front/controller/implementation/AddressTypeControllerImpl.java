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
import com.example.demo.front.controller.interfaces.AddressTypeController;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.back.service.interfaces.AddressTypeService;

@Controller
public class AddressTypeControllerImpl implements AddressTypeController{

	@Autowired
	private BusinessDelegate businessDelegate;
	
	public AddressTypeControllerImpl() {
	}

	@Override
	@GetMapping("/addrtype/")
	public String indexAddressType(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("addrtypes", businessDelegate.findAllAddresstypes());
		}else {
			List<Addresstype> addrtypes = new ArrayList<>();
			addrtypes.add(businessDelegate.findAddresstypeById(id.intValue()));
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
			businessDelegate.saveAddresstype(addrtype);
		}
		return "redirect:/addrtype/";
	}

	@Override
	@GetMapping("/addrtype/del/{id}")
	public String deleteAddressType(@PathVariable("id") long id, Model model) {
		Addresstype addrtype = businessDelegate.findAddresstypeById((int)id);
		businessDelegate.deleteAddresstype(addrtype);
		model.addAttribute("addrtypes", businessDelegate.findAllAddresstypes());
		return "addrtype/index";
	}

	@Override
	@GetMapping("/addrtype/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Addresstype addrtype = businessDelegate.findAddresstypeById((int)id);
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
			businessDelegate.editAddresstype(addrtype);
			model.addAttribute("addrtypes", businessDelegate.findAllAddresstypes());
		}
		return "redirect:/addrtype/";
	}
	
}
