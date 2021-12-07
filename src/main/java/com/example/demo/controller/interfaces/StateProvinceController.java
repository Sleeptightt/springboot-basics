package com.example.demo.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.person.Stateprovince;

public interface StateProvinceController {
	
	public String indexStateProvince(@RequestParam(required = false, value = "id") Long id, Model model);
	public String addStateProvince(Model model);
	public String saveStateProvince(@ModelAttribute("stprov") @Validated Stateprovince stprov, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deleteStateProvince(@PathVariable("id") long id, Model model);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updateStateProvince(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("stprov") @Validated Stateprovince stprov, BindingResult bindingResult, Model model);
	
}
