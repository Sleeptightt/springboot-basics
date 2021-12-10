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

import com.example.demo.front.controller.interfaces.StateProvinceController;
import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.back.service.interfaces.StateProvinceService;

@Controller
public class StateProvinceControllerImpl implements StateProvinceController{
	
	private StateProvinceService stprovService;
	
	@Autowired
	public StateProvinceControllerImpl(StateProvinceService stprovService) {
		this.stprovService = stprovService;
	}

	@Override
	@GetMapping("/stprov/")
	public String indexStateProvince(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("stprovs", stprovService.findAll());
		}else {
			List<Stateprovince> stprovs = new ArrayList<>();
			stprovs.add(stprovService.findById(id).get());
			model.addAttribute("stprovs", stprovs);
		}
		return "stprov/index";
	}

	@Override
	@GetMapping("/stprov/add")
	public String addStateProvince(Model model) {
		model.addAttribute("stprov", new Stateprovince());
		return "stprov/add-stprov";
	}

	@Override
	@PostMapping("/stprov/add")
	public String saveStateProvince(@ModelAttribute("stprov") @Validated Stateprovince stprov, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("stprov", stprov);
				return "stprov/add-stprov";
			}
			stprovService.saveStateprovince(stprov);
		}
		return "redirect:/stprov/";
	}

	@Override
	@GetMapping("/stprov/del/{id}")
	public String deleteStateProvince(@PathVariable("id") long id, Model model) {
		Stateprovince stprov = stprovService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		stprovService.delete(stprov);
		model.addAttribute("stprovs", stprovService.findAll());
		return "stprov/index";
	}

	@Override
	@GetMapping("/stprov/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Stateprovince stprov = stprovService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("stprov", stprov);
		return "stprov/update-stprov";
	}

	@Override
	@PostMapping("/stprov/edit/{id}")
	public String updateStateProvince(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("stprov") @Validated Stateprovince stprov, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("stprov", stprov);
				return "stprov/update-stprov";		
			}
			stprovService.updateStateprovince(stprov);
			model.addAttribute("stprovs", stprovService.findAll());
		}
		return "redirect:/stprov/";
	}

}
