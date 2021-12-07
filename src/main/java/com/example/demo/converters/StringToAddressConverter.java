package com.example.demo.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import com.example.demo.daos.AddressDao;
import com.example.demo.daos.PersonDao;
import com.example.demo.model.person.Address;
import com.example.demo.model.person.Person;

@WritingConverter
@Component
public class StringToAddressConverter implements Converter<String,Address>{

	@Autowired
	private AddressDao addressDao;
	
	@Override
	public Address convert(String addressid) {
		return addressDao.get(Integer.parseInt(addressid)).orElse(null);
	}
	
}
