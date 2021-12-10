package com.example.demo.back.service;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.front.model.person.Addresstype;
import com.example.demo.back.repository.AddressTypeRepository;
import com.example.demo.back.service.interfaces.AddressTypeService;

@Service
public class AddressTypeServiceImpl implements AddressTypeService{

	private AddressTypeRepository addressTypeRepository;

	@Autowired
	public AddressTypeServiceImpl(AddressTypeRepository addressTypeRepository) {
		super();
		this.addressTypeRepository = addressTypeRepository;
	}
	
	public Addresstype saveAddressType(Addresstype addressType) {
		if(addressType == null)
			return null;
		addressType = addressTypeRepository.save(addressType);
		return addressType;
	}

	public boolean existsById(Integer addressTypeid) {
		return addressTypeRepository.existsById(addressTypeid);
	}

	@Override
	public Iterable<Addresstype> findAll() {
		return addressTypeRepository.findAll();
	}

	@Override
	public Optional<Addresstype> findById(long id) {
		return addressTypeRepository.findById((int) id);
	}

	@Override
	public void delete(Addresstype addrtype) {
		addressTypeRepository.delete(addrtype);
	}

	@Override
	@Transactional
	public Addresstype updateAddressType(Addresstype addrtype) {
		if(addrtype == null)
			return null;
		Addresstype at = addressTypeRepository.findById(addrtype.getAddresstypeid()).get();
		at.setName(addrtype.getName());
		at.setModifieddate(addrtype.getModifieddate());
		at.setRowguid(addrtype.getRowguid());
		return addrtype;
	}
}
