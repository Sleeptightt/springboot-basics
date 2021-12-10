package com.example.demo.front.controller.implementation;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.front.controller.interfaces.LoginController;
import com.example.demo.front.model.person.UserApp;

@Controller
public class LoginControllerImpl implements LoginController{
	
	@Override
	@GetMapping("/login")
	public String customLogin(Model model) {
		model.addAttribute("user", new UserApp());
		return "custom-login";
	}
	
	@Override
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@Override
	@GetMapping("/access-denied")
	public String accessDeniedPage(@Param(value = "url") String url, Model model) {
		if(url == null) {
			url = "/";
		}
		model.addAttribute("url", url);
		return "denied";
	}
}
