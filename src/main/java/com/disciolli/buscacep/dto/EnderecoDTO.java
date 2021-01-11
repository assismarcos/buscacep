package com.disciolli.buscacep.dto;

import io.swagger.annotations.ApiModel;

@ApiModel("Endereco")
public class EnderecoDTO {

	private String rua;
	private String bairro;
	private String cidade;
	private String estado;

	public EnderecoDTO(String rua, String bairro, String cidade, String estado) {
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
