package com.disciolli.buscacep.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.disciolli.buscacep.dto.EnderecoDTO;
import com.disciolli.buscacep.model.Endereco;
import com.disciolli.buscacep.service.EnderecoService;

@RestController
public class EnderecoController {

	private EnderecoService enderecoService;

	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}
	
	@GetMapping(path = "/endereco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EnderecoDTO> buscaEnderecoPorCep(@Valid @RequestBody CepRequest cep) {

		Optional<Endereco> endereco = enderecoService.buscarEnderecoPorCep(cep.getCep());
		return endereco.isPresent() ? ResponseEntity.ok(endereco.get().toDTO()) : ResponseEntity.notFound().build();
	}

}
