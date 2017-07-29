package com.economic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.economic.model.Lancamento;
import com.economic.model.Pessoa;
import com.economic.repository.LancamentoRepository;
import com.economic.repository.PessoaRepository;
import com.economic.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		
		if(pessoa == null || pessoa.isInativo())
			throw new PessoaInexistenteOuInativaException();
		
		return lancamentoRepository.save(lancamento);
	}

	public void remover(Long id) {
		lancamentoRepository.delete(id);
	}
	
}