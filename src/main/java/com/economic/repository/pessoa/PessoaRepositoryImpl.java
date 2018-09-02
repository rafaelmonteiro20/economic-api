package com.economic.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.economic.model.Pessoa;
import com.economic.model.Pessoa_;
import com.economic.repository.filter.PessoaFilter;

@Repository
public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Pessoa> pesquisar(PessoaFilter filter) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		
		criteria.select(root);
		criteria.where(criarRestricoes(builder, criteria, root, filter));
		
		return manager.createQuery(criteria).getResultList();
	}

	private Predicate[] criarRestricoes(CriteriaBuilder builder, CriteriaQuery<Pessoa> criteria,
			Root<Pessoa> root, PessoaFilter filter) {

		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(
				builder.lower(root.get(Pessoa_.nome)), "%" + filter.getNome().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[0]);
	}

}
