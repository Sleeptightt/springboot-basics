package com.example.demo.back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.front.model.person.Addresstype;

@Repository
public interface AddressTypeRepository extends CrudRepository<Addresstype, Integer>{

}
