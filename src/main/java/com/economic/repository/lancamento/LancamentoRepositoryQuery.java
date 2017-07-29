package com.economic.repository.lancamento;

import java.util.List;

import com.economic.model.Lancamento;
import com.economic.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter);
	
}