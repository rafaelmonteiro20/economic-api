package com.economic.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.economic.dto.LancamentoCategoria;
import com.economic.dto.LancamentoDia;
import com.economic.dto.LancamentoPessoa;
import com.economic.dto.ResumoLancamento;
import com.economic.model.Categoria_;
import com.economic.model.Lancamento;
import com.economic.model.Lancamento_;
import com.economic.model.Pessoa_;
import com.economic.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable pageable) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, count(filter));
	}
	
	@Override
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(ResumoLancamento.class,
				root.get(Lancamento_.id), root.get(Lancamento_.descricao),
				root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento),
				root.get(Lancamento_.valor), root.get(Lancamento_.tipo),
				root.get(Lancamento_.categoria).get(Categoria_.nome),
				root.get(Lancamento_.pessoa).get(Pessoa_.nome)));
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<ResumoLancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, count(filter));
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(filter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get(Lancamento_.descricao)), "%" + filter.getDescricao().toLowerCase() + "%"));
		}
		
		if (filter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), filter.getDataVencimentoDe()));
		}
		
		if (filter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), filter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	@Override
	public Long count(LancamentoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<LancamentoCategoria> porCategoria(LocalDate mesReferencia) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoCategoria> criteria = builder.createQuery(LancamentoCategoria.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(LancamentoCategoria.class, 
				root.get(Lancamento_.categoria),
				builder.sum(root.get(Lancamento_.valor))));
		
		LocalDate dataInicio = mesReferencia.withDayOfMonth(1);
		LocalDate dataFim = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataInicio),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataFim));
		
		criteria.groupBy(root.get(Lancamento_.categoria));
		
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public List<LancamentoDia> porDia(LocalDate mesReferencia) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoDia> criteria = builder.createQuery(LancamentoDia.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(LancamentoDia.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.dataVencimento),
				builder.sum(root.get(Lancamento_.valor))));
		
		LocalDate dataInicio = mesReferencia.withDayOfMonth(1);
		LocalDate dataFim = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataInicio),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), dataFim));
		
		criteria.groupBy(root.get(Lancamento_.tipo), 
						 root.get(Lancamento_.dataVencimento));
		
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public List<LancamentoPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoPessoa> criteria = builder.createQuery(LancamentoPessoa.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(LancamentoPessoa.class, 
				root.get(Lancamento_.tipo),
				root.get(Lancamento_.pessoa).get(Pessoa_.nome),
				builder.sum(root.get(Lancamento_.valor))));
		
		criteria.where(
				builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), inicio),
				builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), fim));
		
		criteria.groupBy(root.get(Lancamento_.tipo), 
						 root.get(Lancamento_.pessoa));
		
		return manager.createQuery(criteria).getResultList();
	}

}
