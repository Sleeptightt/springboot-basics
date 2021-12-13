package com.example.demo.front.controller.implementation;

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
import com.example.demo.front.controller.interfaces.BusinessEntityAddressController;
import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.person.BusinessentityaddressPK;
import com.example.demo.back.service.interfaces.AddressService;
import com.example.demo.back.service.interfaces.AddressTypeService;
import com.example.demo.back.service.interfaces.BusinessEntityAddressService;
import com.example.demo.back.service.interfaces.BusinessEntityService;

@Controller
public class BusinessEntityAddressControllerImpl implements BusinessEntityAddressController{

	@Autowired
	private BusinessDelegate businessDelegate;
	
	public BusinessEntityAddressControllerImpl() {
	}

	@Override
	@GetMapping("/benaddr/")
	public String indexBusinessEntityAdress(@RequestParam(required = false, value = "id1") Long id1, @RequestParam(required = false, value = "id2") Long id2, @RequestParam(required = false, value = "id3") Long id3, Model model) {
		if(id1 != null) {
			model.addAttribute("benaddrs", businessDelegate.findAllBusinessentityaddressByAddress(id1.intValue()));
		}else if(id2 != null) {
			model.addAttribute("benaddrs", businessDelegate.findAllBusinessentityaddressByAddresstype(id2.intValue()));
		}else if(id3 != null) {
			model.addAttribute("benaddrs", businessDelegate.findAllBusinessentityaddressByBusinessentity(id3.intValue()));
		}else {
			model.addAttribute("benaddrs", businessDelegate.findAllBusinessentityaddress());
		}
		return "benaddr/index";
	}

	@Override
	@GetMapping("/benaddr/add")
	public String addBusinessEntityAdress(Model model) {
		model.addAttribute("benaddr", new Businessentityaddress());
		model.addAttribute("addrs", businessDelegate.findAllAddresses());
		model.addAttribute("addrtypes", businessDelegate.findAllAddresstypes());
		model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		return "benaddr/add-benaddr";
	}

	@Override
	@PostMapping("/benaddr/add")
	public String saveBusinessEntityAdress(@ModelAttribute("benaddr") @Validated Businessentityaddress benaddr, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("benaddr", new Businessentityaddress());
				model.addAttribute("addrs", businessDelegate.findAllAddresses());
				model.addAttribute("addrtypes", businessDelegate.findAllAddresstypes());
				model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
				return "benaddr/add-benaddr";
			}
			BusinessentityaddressPK id = new BusinessentityaddressPK();
			id.setAddressid((int) benaddr.getAddress().getAddressid()); id.setAddresstypeid((int) benaddr.getAddresstype().getAddresstypeid()); id.setBusinessentityid((int) benaddr.getBusinessentity().getBusinessentityid());
			benaddr.setId(id);
			businessDelegate.saveBusinessentityaddress(benaddr);
		}
		return "redirect:/benaddr/";
	}

	@Override
	@GetMapping("/benaddr/del/{id1}&{id2}&{id3}")
	public String deleteBusinessEntityAdress(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, Model model) {
		BusinessentityaddressPK id = new BusinessentityaddressPK();
		id.setAddressid((int) id1); id.setAddresstypeid((int) id2); id.setBusinessentityid((int) id3);
		Businessentityaddress benaddr = businessDelegate.findBusinessentityaddressById(id);
		businessDelegate.deleteBusinessentityaddress(benaddr);
		model.addAttribute("benaddrs", businessDelegate.findAllBusinessentityaddress());
		return "benaddr/index";
	}

	@Override
	@GetMapping("/benaddr/edit/{id1}&{id2}&{id3}")
	public String showUpdateForm(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, Model model) {
		BusinessentityaddressPK id = new BusinessentityaddressPK();
		id.setAddressid((int) id1); id.setAddresstypeid((int) id2); id.setBusinessentityid((int) id3);
		Businessentityaddress benaddr = businessDelegate.findBusinessentityaddressById(id);
		model.addAttribute("benaddr", benaddr);
		model.addAttribute("addrs", businessDelegate.findAllAddresses());
		model.addAttribute("addrtypes", businessDelegate.findAllAddresstypes());
		model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
		return "benaddr/update-benaddr";
	}

	@Override
	@PostMapping("/benaddr/edit/{id1}&{id2}&{id3}")
	public String updateBusinessEntityAdress(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("benaddr") @Validated Businessentityaddress benaddr, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("benaddr", new Businessentityaddress());
				model.addAttribute("addrs", businessDelegate.findAllAddresses());
				model.addAttribute("addrtypes", businessDelegate.findAllAddresstypes());
				model.addAttribute("bens", businessDelegate.findAllBusinessentitys());
				return "benaddr/update-benaddr";
			}
			BusinessentityaddressPK id = new BusinessentityaddressPK();
			id.setAddressid((int) benaddr.getAddress().getAddressid()); id.setAddresstypeid((int) benaddr.getAddresstype().getAddresstypeid()); id.setBusinessentityid((int) benaddr.getBusinessentity().getBusinessentityid());
			benaddr.setId(id);
			businessDelegate.editBusinessentityaddress(benaddr);
			model.addAttribute("benaddrs", businessDelegate.findAllBusinessentityaddress());
		}
		return "redirect:/benaddr/";
	}

}
