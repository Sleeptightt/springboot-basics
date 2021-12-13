package com.example.demo.front.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.front.model.sales.Store;


public interface StoreController {

	public String indexStore(@RequestParam(required = false, value = "id") Long id, Model model);
	public String addStore(Model model);
	public String saveStore(@ModelAttribute("store") @Validated Store stprov, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deleteStore(@PathVariable("id") long id, Model model);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updateStore(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("store") @Validated Store stprov, BindingResult bindingResult, Model model);
	
	
}
