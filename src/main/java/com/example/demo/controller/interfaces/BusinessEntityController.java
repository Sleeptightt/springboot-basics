package com.example.demo.controller.interfaces;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.person.Businessentity;

public interface BusinessEntityController {

	public String indexBusinessEntity(@RequestParam(required = false, value = "id") Long id, Model model);
}
