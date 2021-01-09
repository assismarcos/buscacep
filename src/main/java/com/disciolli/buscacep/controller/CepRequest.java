package com.disciolli.buscacep.controller;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;

public class CepRequest {

	@ApiModelProperty(value = "CEP", example = "01310-100", required = true)
	@Pattern(regexp = "\\d{8}|[0-9]{5}-[0-9]{3}", message = "CEP inválido")
	private String cep;

	public CepRequest() {
	}

	public CepRequest(String cep) {
		setCep(cep);
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep != null ? cep.replaceAll("[^0-9]", "") : null;
	}
	
}
