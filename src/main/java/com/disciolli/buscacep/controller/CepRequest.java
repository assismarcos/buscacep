package com.disciolli.buscacep.controller;

import javax.validation.constraints.Pattern;

public class CepRequest {

	@Pattern(regexp = "\\d{8}", message = "CEP inválido")
	private String cep;

	public CepRequest() {
	}

	public CepRequest(String cep) {
		this.cep = cep;
	}

	public String getCep() {
		return cep;
	}

	public void setNumero(String cep) {
		this.cep = cep;
	}

}
