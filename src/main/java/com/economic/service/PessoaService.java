package com.economic.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.economic.model.Pessoa;
import com.economic.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa salvar(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa existente = buscarPessoa(id);
		
		BeanUtils.copyProperties(pessoa, existente, "id");
		
		return pessoaRepository.save(existente);
	}
	
	public void mudarStatus(Long id) {
		Pessoa pessoa = buscarPessoa(id);
		pessoa.mudarStatus();
		pessoaRepository.save(pessoa);
	}

	private Pessoa buscarPessoa(Long id) {
		Pessoa pessoa = pessoaRepository.findOne(id);
		
		if(pessoa == null)
			throw new EmptyResultDataAccessException(1);
		
		return pessoa;
	}

	public void remover(Long id) {
		pessoaRepository.delete(id);
	}
	
}