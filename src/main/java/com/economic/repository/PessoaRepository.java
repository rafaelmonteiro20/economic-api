package com.economic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.economic.model.Pessoa;
import com.economic.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}
