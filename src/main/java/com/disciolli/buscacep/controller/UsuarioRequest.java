package com.disciolli.buscacep.controller;

import io.swagger.annotations.ApiModelProperty;

public class UsuarioRequest {

	@ApiModelProperty(position = 0, required = true)
	private String usuario;
	
	@ApiModelProperty(position = 1, required = true)
	private String senha;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
