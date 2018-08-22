package com.economic.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.economic.model.TipoLancamento;

public class ResumoLancamento {

	private Long id;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private TipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	public ResumoLancamento(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {
		
		this.id = id;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getPessoa() {
		return pessoa;
	}

}