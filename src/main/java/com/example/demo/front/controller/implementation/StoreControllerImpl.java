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
import com.example.demo.front.controller.interfaces.StoreController;
import com.example.demo.front.model.sales.Store;

@Controller
public class StoreControllerImpl implements StoreController{
	
	@Autowired
	private BusinessDelegate businessDelegate;
	
	public StoreControllerImpl() {
		
	}

	@Override
	@GetMapping("/store/")
	public String indexStore(@RequestParam(required = false, value = "id") Long id, Model model) {
		if(id == null) {
			model.addAttribute("stores", businessDelegate.findAllStores());
		}else {
			List<Store> stores = new ArrayList<>();
			stores.add(businessDelegate.findStoreById(id.intValue()));
			model.addAttribute("stores", stores);
		}
		return "store/index";
	}

	@Override
	@GetMapping("/store/add")
	public String addStore(Model model) {
		model.addAttribute("store", new Store());
		model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		return "store/add-store";
	}

	@Override
	@PostMapping("/store/add")
	public String saveStore(@ModelAttribute("store") @Validated Store store, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("store", store);
				model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
				return "store/add-store";
			}
			businessDelegate.saveStore(store);
		}
		return "redirect:/store/";
	}

	@Override
	@GetMapping("/store/del/{id}")
	public String deleteStore(@PathVariable("id") long id, Model model) {
		Store store = businessDelegate.findStoreById((int)id);
		businessDelegate.deleteStore(store);
		model.addAttribute("stores", businessDelegate.findAllStores());
		return "store/index";
	}

	@Override
	@GetMapping("/store/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Store store = businessDelegate.findStoreById((int)id);
		model.addAttribute("store", store);
		model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		return "store/update-store";
	}

	@Override
	@PostMapping("/store/edit/{id}")
	public String updateStore(@PathVariable("id") long id, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("store") @Validated Store store, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("store", store);
				model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
				return "store/update-store";		
			}
			businessDelegate.editStore(store);
			model.addAttribute("stores", businessDelegate.findAllStores());
		}
		return "redirect:/store/";
	}
}
