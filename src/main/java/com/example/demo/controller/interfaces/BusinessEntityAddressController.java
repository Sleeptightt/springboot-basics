package com.example.demo.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.person.Addresstype;
import com.example.demo.model.person.Businessentityaddress;

public interface BusinessEntityAddressController {
	
	public String indexBusinessEntityAdress(@RequestParam(required = false, value = "id1") Long id1, @RequestParam(required = false, value = "id2") Long id2, @RequestParam(required = false, value = "id3") Long id3, Model model);
	public String addBusinessEntityAdress(Model model);
	public String saveBusinessEntityAdress(@ModelAttribute("benaddr") @Validated Businessentityaddress benaddr, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deleteBusinessEntityAdress(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, Model model);
	public String showUpdateForm(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, Model model);
	public String updateBusinessEntityAdress(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("benaddr") @Validated Businessentityaddress benaddr, BindingResult bindingResult, Model model);
	
	
}
