package com.economic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economic.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}