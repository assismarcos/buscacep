package com.disciolli.buscacep.controller;

import javax.validation.constraints.Pattern;

public class Cep {

	@Pattern(regexp = "\\d{8}", message = "CEP inválido")
	private String numero;

	public Cep() {
	}

	public Cep(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
