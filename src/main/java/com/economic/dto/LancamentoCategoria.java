package com.economic.dto;

import java.math.BigDecimal;

import com.economic.model.Categoria;

public class LancamentoCategoria {

	private Categoria categoria;
	private BigDecimal total;
	
	public LancamentoCategoria(Categoria categoria, BigDecimal total) {
		this.categoria = categoria;
		this.total = total;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
}
