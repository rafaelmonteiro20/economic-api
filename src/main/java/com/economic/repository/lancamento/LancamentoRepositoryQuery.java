package com.economic.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.economic.dto.LancamentoCategoria;
import com.economic.dto.LancamentoDia;
import com.economic.dto.LancamentoPessoa;
import com.economic.dto.ResumoLancamento;
import com.economic.model.Lancamento;
import com.economic.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable pageable);
	
	Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
	
	List<LancamentoCategoria> porCategoria(LocalDate mesReferencia);

	List<LancamentoDia> porDia(LocalDate mesReferencia);

	List<LancamentoPessoa> porPessoa(LocalDate inicio, LocalDate fim);
	
	Long count(LancamentoFilter filter);

}
