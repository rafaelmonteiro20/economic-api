package com.economic.service;

import org.springframework.beans.BeanUtils;
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
		validarPessoa(lancamento);
		return lancamentoRepository.save(lancamento);
	}

	public Lancamento atualizar(Long id, Lancamento lancamento) {
		Lancamento lancamentoSalvo = this.buscarLancamento(id);
		
		if(!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		return lancamentoRepository.save(lancamentoSalvo);
	}
	
	private void validarPessoa(Lancamento lancamento) {
		
		Pessoa pessoa = lancamento.getPessoa();
		
		if(pessoa.getId() != null) {
			pessoa = pessoaRepository.findOne(pessoa.getId());
		}
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}
	
	private Lancamento buscarLancamento(Long id) {
		Lancamento lancamento = lancamentoRepository.findOne(id);
		
		if (lancamento == null) {
			throw new IllegalArgumentException();
		}

		return lancamento;
	}
	
	public void remover(Long id) {
		lancamentoRepository.delete(id);
	}
	
}