package com.example.demo.controller.implementation;

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

import com.example.demo.controller.interfaces.BusinessEntityAddressController;
import com.example.demo.model.person.Address;
import com.example.demo.model.person.Addresstype;
import com.example.demo.model.person.Businessentity;
import com.example.demo.model.person.Businessentityaddress;
import com.example.demo.model.person.BusinessentityaddressPK;
import com.example.demo.service.interfaces.AddressService;
import com.example.demo.service.interfaces.AddressTypeService;
import com.example.demo.service.interfaces.BusinessEntityAddressService;
import com.example.demo.service.interfaces.BusinessEntityService;

@Controller
public class BusinessEntityAddressControllerImpl implements BusinessEntityAddressController{

	private BusinessEntityAddressService benaddrService;
	private AddressService addrService;
	private AddressTypeService addrtypeService;
	private BusinessEntityService benService;
	
	@Autowired
	public BusinessEntityAddressControllerImpl(BusinessEntityAddressService benaddrService, AddressService addrService,
			AddressTypeService addrtypeService, BusinessEntityService benService) {
		this.benaddrService = benaddrService;
		this.addrService = addrService;
		this.addrtypeService = addrtypeService;
		this.benService = benService;
	}

	@Override
	@GetMapping("/benaddr/")
	public String indexBusinessEntityAdress(@RequestParam(required = false, value = "id1") Long id1, @RequestParam(required = false, value = "id2") Long id2, @RequestParam(required = false, value = "id3") Long id3, Model model) {
		if(id1 != null) {
			Address addr = addrService.findById(id1).get();
			model.addAttribute("benaddrs", benaddrService.findAllByAddress(addr));
		}else if(id2 != null) {
			Addresstype addrtype = addrtypeService.findById(id2).get();
			model.addAttribute("benaddrs", benaddrService.findAllByAddresstype(addrtype));
		}else if(id3 != null) {
			Businessentity ben = benService.findById(id3).get();
			model.addAttribute("benaddrs", benaddrService.findAllByBusinessentity(ben));
		}else {
			model.addAttribute("benaddrs", benaddrService.findAll());
		}
		return "benaddr/index";
	}

	@Override
	@GetMapping("/benaddr/add")
	public String addBusinessEntityAdress(Model model) {
		model.addAttribute("benaddr", new Businessentityaddress());
		model.addAttribute("addrs", addrService.findAll());
		model.addAttribute("addrtypes", addrtypeService.findAll());
		model.addAttribute("bens", benService.findAll());
		return "benaddr/add-benaddr";
	}

	@Override
	@PostMapping("/benaddr/add")
	public String saveBusinessEntityAdress(@ModelAttribute("benaddr") @Validated Businessentityaddress benaddr, BindingResult result, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (result.hasErrors()) {
				model.addAttribute("benaddr", new Businessentityaddress());
				model.addAttribute("addrs", addrService.findAll());
				model.addAttribute("addrtypes", addrtypeService.findAll());
				model.addAttribute("bens", benService.findAll());
				return "benaddr/add-benaddr";
			}
			BusinessentityaddressPK id = new BusinessentityaddressPK();
			id.setAddressid((int) benaddr.getAddress().getAddressid()); id.setAddresstypeid((int) benaddr.getAddresstype().getAddresstypeid()); id.setBusinessentityid((int) benaddr.getBusinessentity().getBusinessentityid());
			benaddr.setId(id);
			benaddrService.saveBusinessEntityAddress(benaddr);
		}
		return "redirect:/benaddr/";
	}

	@Override
	@GetMapping("/benaddr/del/{id1}&{id2}&{id3}")
	public String deleteBusinessEntityAdress(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, Model model) {
		BusinessentityaddressPK id = new BusinessentityaddressPK();
		id.setAddressid((int) id1); id.setAddresstypeid((int) id2); id.setBusinessentityid((int) id3);
		Businessentityaddress benaddr = benaddrService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		benaddrService.delete(benaddr);
		model.addAttribute("benaddrs", benaddrService.findAll());
		return "benaddr/index";
	}

	@Override
	@GetMapping("/benaddr/edit/{id1}&{id2}&{id3}")
	public String showUpdateForm(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, Model model) {
		BusinessentityaddressPK id = new BusinessentityaddressPK();
		id.setAddressid((int) id1); id.setAddresstypeid((int) id2); id.setBusinessentityid((int) id3);
		Businessentityaddress benaddr = benaddrService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("benaddr", benaddr);
		model.addAttribute("addrs", addrService.findAll());
		model.addAttribute("addrtypes", addrtypeService.findAll());
		model.addAttribute("bens", benService.findAll());
		return "benaddr/update-benaddr";
	}

	@Override
	@PostMapping("/benaddr/edit/{id1}&{id2}&{id3}")
	public String updateBusinessEntityAdress(@PathVariable("id1") long id1, @PathVariable("id2") long id2, @PathVariable("id3") long id3, @RequestParam(value = "action", required = true) String action,
			@ModelAttribute("benaddr") @Validated Businessentityaddress benaddr, BindingResult bindingResult, Model model) {
		if (action != null && !action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("benaddr", new Businessentityaddress());
				model.addAttribute("addrs", addrService.findAll());
				model.addAttribute("addrtypes", addrtypeService.findAll());
				model.addAttribute("bens", benService.findAll());
				return "benaddr/update-benaddr";
			}
			BusinessentityaddressPK id = new BusinessentityaddressPK();
			id.setAddressid((int) benaddr.getAddress().getAddressid()); id.setAddresstypeid((int) benaddr.getAddresstype().getAddresstypeid()); id.setBusinessentityid((int) benaddr.getBusinessentity().getBusinessentityid());
			benaddr.setId(id);
			benaddrService.updateBusinessEntityAddress(benaddr);
			model.addAttribute("benaddrs", benaddrService.findAll());
		}
		return "redirect:/benaddr/";
	}

}
