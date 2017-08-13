package com.economic.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.economic.model.Lancamento;
import com.economic.repository.filter.LancamentoFilter;
import com.economic.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {

	Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable pageable);
	
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
	
}