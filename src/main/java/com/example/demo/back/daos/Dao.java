package com.example.demo.back.daos;

import java.util.List;
import java.util.Optional;

public interface Dao<T,K> {
	Optional<T> get(K id);
	
	List<T> getAll();
	
	void save(T t);
	
	void update(T t);
	
	void deleteById(K id);
}
