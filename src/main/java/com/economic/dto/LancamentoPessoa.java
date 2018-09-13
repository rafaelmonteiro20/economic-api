package com.economic.dto;

import java.math.BigDecimal;

import com.economic.model.TipoLancamento;

public class LancamentoPessoa {

	private TipoLancamento tipo;
	private String pessoa;
	private BigDecimal total;

	public LancamentoPessoa(TipoLancamento tipo, String pessoa, BigDecimal total) {
		this.tipo = tipo;
		this.pessoa = pessoa;
		this.total = total;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public String getPessoa() {
		return pessoa;
	}

	public BigDecimal getTotal() {
		return total;
	}

}
