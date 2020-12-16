package com.disciolli.buscacep.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.disciolli.buscacep.model.Endereco;
import com.disciolli.buscacep.repository.EnderecoRepository;

@Service
public class EnderecoService {

	private EnderecoRepository enderecoRepository;

	public EnderecoService(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}
	

	public Optional<Endereco> buscarEnderecoPorCep(String cep) {
		return enderecoRepository.findById(cep);
	}
	
	
}
