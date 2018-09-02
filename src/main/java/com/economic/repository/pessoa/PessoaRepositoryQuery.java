package com.economic.repository.pessoa;

import java.util.List;

import com.economic.model.Pessoa;
import com.economic.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {

	List<Pessoa> pesquisar(PessoaFilter filter);
	
}
