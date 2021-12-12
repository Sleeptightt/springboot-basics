package com.example.demo.back.service;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.front.model.person.Phonenumbertype;
import com.example.demo.back.repository.PhoneNumberTypeRepository;
import com.example.demo.back.service.interfaces.PhoneNumberTypeService;

@Service
public class PhoneNumberTypeServiceImpl implements PhoneNumberTypeService{
	
	private PhoneNumberTypeRepository phoneNumberTypeRepository;

	@Autowired
	public PhoneNumberTypeServiceImpl(PhoneNumberTypeRepository phoneNumberTypeRepository) {
		this.phoneNumberTypeRepository = phoneNumberTypeRepository;
	}
	
	public Phonenumbertype savePhoneNumberType(Phonenumbertype phoneNumberType) {
		if(phoneNumberType == null)
			return null;
		phoneNumberType = phoneNumberTypeRepository.save(phoneNumberType);
		return phoneNumberType;
	}
	
	@Transactional
	public Phonenumbertype updatePhoneNumberType(Phonenumbertype phoneNumberType) {
		if(phoneNumberType == null)
			return null;
		Phonenumbertype pt = phoneNumberTypeRepository.findById(phoneNumberType.getPhonenumbertypeid()).get();
		pt.setModifieddate(phoneNumberType.getModifieddate());
		pt.setName(phoneNumberType.getName());
		return phoneNumberType;
	}

	public boolean existsById(Integer phoneNumberTypeid) {
		return phoneNumberTypeRepository.existsById(phoneNumberTypeid);
	}

	@Override
	public Iterable<Phonenumbertype> findAll() {
		return phoneNumberTypeRepository.findAll();
	}

	@Override
	public Optional<Phonenumbertype> findById(long id) {
		return phoneNumberTypeRepository.findById((int) id);
	}

	@Override
	public void delete(Integer id) {
		phoneNumberTypeRepository.deleteById(id);
	}

}
