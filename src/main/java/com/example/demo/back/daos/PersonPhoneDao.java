package com.example.demo.back.daos;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.person.Person;
import com.example.demo.front.model.person.Personphone;
import com.example.demo.front.model.person.PersonphonePK;
import com.example.demo.front.model.person.Phonenumbertype;

@Repository
@Scope("singleton")
public class PersonPhoneDao implements Dao<Personphone, PersonphonePK>{

	@PersistenceContext
	private EntityManager entityManager;
	
	public PersonPhoneDao() {
		
	}
	
	@Override
	public Optional<Personphone> get(PersonphonePK id) {
		return Optional.ofNullable(entityManager.find(Personphone.class, id));
	}

	public Optional<Personphone> findById(PersonphonePK id) {
		return Optional.ofNullable(entityManager.find(Personphone.class, id));
	}
	
	@Override
	public List<Personphone> getAll() {
		Query query = entityManager.createQuery("SELECT ph FROM Personphone ph");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(Personphone t) {
		executeInsideTransaction(entityManager -> entityManager.persist(t));
	}

	@Override
	@Transactional
	public void update(Personphone t) {
		executeInsideTransaction(entityManager -> entityManager.merge(t));
	}

	@Override
	@Transactional
	public void deleteById(PersonphonePK id) {
		Personphone ph = get(id).orElse(null);
		executeInsideTransaction(entityManager -> entityManager.remove(ph));
		
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
	
	public List<Personphone> findAllByPhonenumbertype(Integer phonenumbertype) {
		Query query = entityManager.createQuery("SELECT ph FROM Personphone ph WHERE ph.phonenumbertype.phonenumbertypeid = :phonenumbertypeid");
		query.setParameter("phonenumbertypeid", phonenumbertype);
		return query.getResultList();
	}
	
	public List<Personphone> findAllByPhonenumberLike(String phonenumber) {
		//Query query = entityManager.createQuery("SELECT ph FROM Personphone ph WHERE ph.id.phonenumber LIKE 'phnumber%'");
		Query query = entityManager.createQuery("SELECT ph FROM Personphone ph WHERE ph.id.phonenumber = :phnumber");
		query.setParameter("phnumber", phonenumber);
		return query.getResultList();
	}
	
	public List<Personphone> findAllWithAtLeastTwoPersons() {
		String q = "SELECT ph FROM Personphone ph GROUP BY ph.person HAVING count(ph) >= 2"; // works with postgresql and presumably with h2
		//String q = "SELECT l.precondition FROM Localcondition l WHERE l.threshold.thresValue > 1 GROUP BY l.precondition.preconId HAVING count(*) >= 2"; // works only in h2, do not know why
		//String q = "SELECT l.precondition FROM Localcondition l WHERE l.threshold.thresValue > '1' GROUP BY l.precondition HAVING count(l) >= 2"; // the same sa the one above but with type safety
		Query query = entityManager.createQuery(q);
		return query.getResultList();
	}

	public List<Personphone> findAllByPerson(Integer person){
		Query query = entityManager.createQuery("SELECT ph FROM Personphone ph WHERE ph.person.businessentityid = :personid");
		query.setParameter("personid", person);
		return query.getResultList();
	}
	
}
