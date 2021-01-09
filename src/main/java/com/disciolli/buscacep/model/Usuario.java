package com.disciolli.buscacep.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario {

	@Id
	private String nome;

	@NotEmpty
	private String senha;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Funcao funcao;

	public Usuario() {
	}

	public Usuario(String nome, String senha, Funcao funcao) {
		this.nome = nome;
		this.senha = senha;
		this.funcao = funcao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

}
