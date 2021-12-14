package com.example.demo.front.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.front.model.sales.Customer;

public interface CustomerController {

	public String indexCustomer(@RequestParam(required = false, value = "id") Long id, Model model);
	public String addCustomer(Model model);
	public String saveCustomer(@ModelAttribute("customer") @Validated Customer customer, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action);
	public String deleteCustomer(@PathVariable("id") long id, Model model);
	public String showUpdateForm(@PathVariable("id") long id, Model model);
	public String updateCustomer(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("customer") @Validated Customer customer, BindingResult bindingResult, Model model);
	
	
}
