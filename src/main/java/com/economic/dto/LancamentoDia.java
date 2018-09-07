package com.economic.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.economic.model.TipoLancamento;

public class LancamentoDia {

	private TipoLancamento tipo;
	private LocalDate data;
	private BigDecimal valor;
	
	public LancamentoDia(TipoLancamento tipo, LocalDate data, BigDecimal valor) {
		this.tipo = tipo;
		this.data = data;
		this.valor = valor;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public LocalDate getData() {
		return data;
	}

	public BigDecimal getValor() {
		return valor;
	}
	
}
