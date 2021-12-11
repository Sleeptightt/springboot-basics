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
import com.example.demo.front.controller.interfaces.BusinessEntityController;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.back.service.interfaces.BusinessEntityService;

@Controller
public class BusinessEntityControllerImpl implements BusinessEntityController{
	
	@Autowired
	private BusinessDelegate businessDelegate;
	
	public BusinessEntityControllerImpl() {
	}

	@Override
	@GetMapping("/ben/")
	public String indexBusinessEntity(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		}else {
			List<Businessentity> bens = new ArrayList<>();
			bens.add(businessDelegate.findBusinessentityById(id.intValue()));
			model.addAttribute("bens", bens);
		}
		return "ben/index";
	}
}
