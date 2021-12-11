package com.example.demo.back.service;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.front.model.person.Stateprovince;
import com.example.demo.back.repository.StateProvinceRepository;
import com.example.demo.back.service.interfaces.StateProvinceService;

@Service
public class StateProvinceServiceImpl implements StateProvinceService{
	
	private StateProvinceRepository stateProvinceRepository;

	@Autowired
	public StateProvinceServiceImpl(StateProvinceRepository stateProvinceRepository) {
		this.stateProvinceRepository = stateProvinceRepository;
	}
	
	public Stateprovince saveStateProvince(Stateprovince stateProvince) {
		if(stateProvince == null)
			return null;
		return stateProvinceRepository.save(stateProvince);
	}
	
	@Transactional
	public Stateprovince updateStateprovince(Stateprovince stateProvince) {
		if(stateProvince == null)
			return null;
		Stateprovince sp = stateProvinceRepository.findById(stateProvince.getStateprovinceid()).get();
		sp.setName(stateProvince.getName());
		sp.setStateprovincecode(stateProvince.getStateprovincecode());
		sp.setTerritoryid(stateProvince.getTerritoryid());
		sp.setModifieddate(stateProvince.getModifieddate());
		sp.setCountryregion(stateProvince.getCountryregion());
		sp.setRowguid(stateProvince.getRowguid());
		return stateProvince;
	}

	public boolean existsById(Integer stateid) {
		return stateProvinceRepository.existsById(stateid);
	}

	@Override
	public Iterable<Stateprovince> findAll() {
		return stateProvinceRepository.findAll();
	}

	@Override
	public Stateprovince saveStateprovince(Stateprovince stprov) {
		return stateProvinceRepository.save(stprov);
	}

	@Override
	public Optional<Stateprovince> findById(long id) {
		return stateProvinceRepository.findById((int) id);
	}

	@Override
	public void delete(Integer id) {
		stateProvinceRepository.deleteById(id);
	}

}
