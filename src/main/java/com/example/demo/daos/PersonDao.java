package com.example.demo.daos;

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

import com.example.demo.model.person.Person;
import com.example.demo.model.person.Personphone;

@Repository
@Scope("singleton")
public class PersonDao implements Dao<Person, Integer>{

	@PersistenceContext
	private EntityManager entityManager;
	
	public PersonDao() {
		
	}
	
	@Override
	public Optional<Person> get(Integer id) {
		return Optional.ofNullable(entityManager.find(Person.class, id));
	}
	
	public Optional<Person> findById(Integer id) {
		return Optional.ofNullable(entityManager.find(Person.class, id));
	}

	@Override
	public List<Person> getAll() {
		Query query = entityManager.createQuery("SELECT p FROM Person p");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void save(Person person) {
		executeInsideTransaction(entityManager -> entityManager.persist(person));
	}

	@Override
	@Transactional
	public void update(Person person) {
		executeInsideTransaction(entityManager -> entityManager.merge(person));
	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		Person person = get(id).orElse(null);
		executeInsideTransaction(entityManager -> entityManager.remove(person));
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
	
	public List<Person> findAllByBusinessentityid(Integer businessentityid) {
		Query query = entityManager.createQuery("SELECT p FROM Person p WHERE p.businessentity.businessentityid = :benId");
		query.setParameter("benId", businessentityid);
		return query.getResultList();
	}

	public List<Person> findAllByTitle(String title) {
		Query query = entityManager.createQuery("SELECT p FROM Person p WHERE p.title = :title");
		query.setParameter("title", title);
		return query.getResultList();
	}
	
	public List<Person> findAllByPersontype(String persontype) {
		Query query = entityManager.createQuery("SELECT p FROM Person p WHERE p.persontype = :persontype");
		query.setParameter("persontype", persontype);
		return query.getResultList();
	}
	
}
