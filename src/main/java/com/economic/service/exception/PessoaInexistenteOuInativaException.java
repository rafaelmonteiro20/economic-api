package com.economic.service.exception;

public class PessoaInexistenteOuInativaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PessoaInexistenteOuInativaException() {

	}
	
	public PessoaInexistenteOuInativaException(String message) {
		super(message);
	}
	
}