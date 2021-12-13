package com.example.demo.back.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.back.repository.StoreRepository;
import com.example.demo.back.service.interfaces.StoreService;
import com.example.demo.front.model.sales.Store;

import lombok.extern.java.Log;

@Log
@Service
public class StoreServiceImpl implements StoreService{
	
	private StoreRepository storeRepository;

	@Autowired
	public StoreServiceImpl(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}
	
	@Transactional
	public Store updateStore(Store store) {
		if(store == null)
			return null;
		Store sp = storeRepository.findById(store.getBusinessentityid()).get();
		sp.setName(store.getName());
		sp.setDemographics(store.getDemographics());
		sp.setRowguid(store.getRowguid());
		return store;
	}

	public boolean existsById(Integer storeid) {
		return storeRepository.existsById(storeid);
	}

	@Override
	public Iterable<Store> findAll() {
		return storeRepository.findAll();
	}

	@Override
	public Store saveStore(Store stprov) {
		return storeRepository.save(stprov);
	}

	@Override
	public Optional<Store> findById(Integer id) {
		return storeRepository.findById(id);
	}

	@Override
	public void delete(Integer id) {
		storeRepository.deleteById(id);
	}
}
