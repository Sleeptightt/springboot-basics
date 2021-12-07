package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.person.Addresstype;

@Repository
public interface AddressTypeRepository extends CrudRepository<Addresstype, Integer>{

}
