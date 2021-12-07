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

import com.example.demo.controller.interfaces.BusinessEntityController;
import com.example.demo.model.person.Businessentity;
import com.example.demo.model.person.Stateprovince;
import com.example.demo.service.interfaces.BusinessEntityService;
import com.example.demo.service.interfaces.StateProvinceService;

@Controller
public class BusinessEntityControllerImpl implements BusinessEntityController{
	
	private BusinessEntityService benService;
	
	@Autowired
	public BusinessEntityControllerImpl(BusinessEntityService benService) {
		this.benService = benService;
	}

	@Override
	@GetMapping("/ben/")
	public String indexBusinessEntity(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("bens", benService.findAll());
		}else {
			List<Businessentity> bens = new ArrayList<>();
			bens.add(benService.findById(id).get());
			model.addAttribute("bens", bens);
		}
		return "ben/index";
	}
}
