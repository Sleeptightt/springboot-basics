package com.example.demo.front.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import com.example.demo.back.daos.PersonDao;
import com.example.demo.front.model.person.Person;

@WritingConverter
@Component
public class StringToPersonConverter implements Converter<String,Person>{

	@Autowired
	private PersonDao personDao;
	
	@Override
	public Person convert(String businessentityid) {
		return personDao.get(Integer.parseInt(businessentityid)).orElse(null);
	}
	
}
