package com.example.demo.back.daos;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Stateprovince;

@Repository
@Scope("singleton")
public class AddressDao implements Dao<Address,Integer>{

	@PersistenceContext
	private EntityManager entityManager;
	
	public AddressDao() {
		
	}
	
	@Override
	public Optional<Address> get(Integer id) {
		return Optional.ofNullable(entityManager.find(Address.class, id));
	}
	
	public Optional<Address> findById(Integer id) {
		return Optional.ofNullable(entityManager.find(Address.class, id));
	}

	@Override
	public List<Address> getAll() {
		Query query = entityManager.createQuery("SELECT a FROM Address a");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(Address t) {
		executeInsideTransaction(entityManager -> entityManager.persist(t));
	}

	@Override
	@Transactional
	public void update(Address t) {
		executeInsideTransaction(entityManager -> entityManager.merge(t));
		
	}

	private void executeInsideTransaction(Consumer<EntityManager> action) {
		//EntityTransaction tx = entityManager.getTransaction();
		try {
			//tx.begin();
			action.accept(entityManager);
			//tx.commit(); 
		}
		catch (RuntimeException e) {
			//tx.rollback();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void deleteById(Integer id) {
		Address addr = get(id).orElse(null);
		executeInsideTransaction(entityManager -> entityManager.remove(addr));
	}

	public List<Address> findAllByAddressid(Integer addressid) {
		Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.addressid = :addressid");
		query.setParameter("addressid", addressid);
		return query.getResultList();
	}
	
	public List<Address> findAllByModifieddate(Timestamp modifieddate) {
		Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.modifieddate = :modifieddate");
		query.setParameter("modifieddate", modifieddate, TemporalType.TIMESTAMP);
		return query.getResultList();
	}
	
	public List<Address> findAllByStateprovince(Stateprovince stateProvince){
		Query query = entityManager.createQuery("SELECT a FROM Address a WHERE a.stateprovince.stateprovinceid = :stprovId");
		query.setParameter("stprovId", stateProvince.getStateprovinceid());
		return query.getResultList();
	}
}
