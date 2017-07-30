package com.economic.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.economic.model.Lancamento;
import com.economic.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}