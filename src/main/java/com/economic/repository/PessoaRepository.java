package com.economic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economic.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}