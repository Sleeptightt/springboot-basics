package com.example.demo.front.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import com.example.demo.back.repository.BusinessEntityRepository;
import com.example.demo.front.businessdelegate.interfaces.BusinessDelegate;
import com.example.demo.front.model.person.Businessentity;

@WritingConverter
@Component
public class StringToBusinessEntityConverter implements Converter<String,Businessentity>{

	@Autowired
	private BusinessDelegate businessDelegate;
	
	@Override
	public Businessentity convert(String businessentityid) {
		return businessDelegate.findBusinessentityById(Integer.parseInt(businessentityid));
	}

}
