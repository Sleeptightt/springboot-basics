package com.example.demo.back.daos;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.person.Address;
import com.example.demo.front.model.person.Addresstype;
import com.example.demo.front.model.person.Businessentity;
import com.example.demo.front.model.person.Businessentityaddress;
import com.example.demo.front.model.person.BusinessentityaddressPK;

@Repository
@Scope("singleton")
public class BusinessEntityAddressDao implements Dao<Businessentityaddress, BusinessentityaddressPK>{

	@PersistenceContext
	private EntityManager entityManager;
	
	public BusinessEntityAddressDao() {
		
	}
	
	@Override
	public Optional<Businessentityaddress> get(BusinessentityaddressPK id) {
		return Optional.ofNullable(entityManager.find(Businessentityaddress.class, id));
	}
	
	public Optional<Businessentityaddress> findById(BusinessentityaddressPK id) {
		return Optional.ofNullable(entityManager.find(Businessentityaddress.class, id));
	}

	@Override
	public List<Businessentityaddress> getAll() {
		Query query = entityManager.createQuery("SELECT benaddr FROM Businessentityaddress benaddr");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(Businessentityaddress t) {
		executeInsideTransaction(entityManager -> entityManager.persist(t));
	}

	@Override
	@Transactional
	public void update(Businessentityaddress t) {
		executeInsideTransaction(entityManager -> entityManager.merge(t));
		
	}

	@Override
	@Transactional
	public void deleteById(BusinessentityaddressPK id) {
		Businessentityaddress benaddr = get(id).orElse(null);
		executeInsideTransaction(entityManager -> entityManager.remove(benaddr));
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
	
	public List<Businessentityaddress> findAllBetweenTwoDates(Timestamp date1, Timestamp date2) {
		String q = "SELECT ph FROM Personphone ph GROUP BY ph.person HAVING count(ph) >= 2"; // works with postgresql and presumably with h2
		//String q = "SELECT l.precondition FROM Localcondition l WHERE l.threshold.thresValue > 1 GROUP BY l.precondition.preconId HAVING count(*) >= 2"; // works only in h2, do not know why
		//String q = "SELECT l.precondition FROM Localcondition l WHERE l.threshold.thresValue > '1' GROUP BY l.precondition HAVING count(l) >= 2"; // the same sa the one above but with type safety
		Query query = entityManager.createQuery(q);
		return query.getResultList();
	}

	public Iterable<Businessentityaddress> findAllByAddress(Integer addressid) {
		Query query = entityManager.createQuery("SELECT benaddr FROM Businessentityaddress benaddr WHERE benaddr.address.addressid = :addrid");
		query.setParameter("addrid", addressid);
		return query.getResultList();
	}

	public Iterable<Businessentityaddress> findAllByAddresstype(Integer addresstypeid) {
		Query query = entityManager.createQuery("SELECT benaddr FROM Businessentityaddress benaddr WHERE benaddr.addresstype.addresstypeid = :addrtypeid");
		query.setParameter("addrtypeid", addresstypeid);
		return query.getResultList();
	}

	public Iterable<Businessentityaddress> findAllByBusinessentity(Integer businessentityid) {
		Query query = entityManager.createQuery("SELECT benaddr FROM Businessentityaddress benaddr WHERE benaddr.businessentity.businessentityid = :benid");
		query.setParameter("benid", businessentityid);
		return query.getResultList();
	}
	
}
