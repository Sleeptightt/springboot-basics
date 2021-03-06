package com.example.demo.back.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.front.model.person.Businessentity;
import com.example.demo.back.repository.BusinessEntityRepository;
import com.example.demo.back.service.interfaces.BusinessEntityService;

@Service
public class BusinessEntityServiceImpl implements BusinessEntityService{
	
	private BusinessEntityRepository businessEntityRepository;
	
	@Autowired
	public BusinessEntityServiceImpl(BusinessEntityRepository businessEntityRepository) {
		this.businessEntityRepository = businessEntityRepository;
	}
	
	public Businessentity saveBusinessEntity(Businessentity businessEntity) {
		if(businessEntity == null)
			return null;
		businessEntity = businessEntityRepository.save(businessEntity);
		return businessEntity;
	}
	
	@Transactional
	public Businessentity updateBusinessEntity(Businessentity businessEntity) {
		if(businessEntity == null)
			return null;
		Businessentity be = businessEntityRepository.findById(businessEntity.getBusinessentityid()).get();
		be.setName(businessEntity.getName());
		be.setDetails(businessEntity.getDetails());
		be.setPerson(businessEntity.getPerson());
		return businessEntity;
	}

	public boolean existsById(Integer businessEntityid) {
		return businessEntityRepository.existsById(businessEntityid);
	}

	@Override
	public Iterable<Businessentity> findAll() {
		return businessEntityRepository.findAll();
	}

	@Override
	public Optional<Businessentity> findById(long id) {
		return businessEntityRepository.findById((int) id);
	}

	@Override
	public void delete(Integer id) {
		businessEntityRepository.deleteById(id);
	}
}
