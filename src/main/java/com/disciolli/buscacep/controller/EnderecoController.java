package com.disciolli.buscacep.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disciolli.buscacep.model.Endereco;
import com.disciolli.buscacep.service.EnderecoService;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

	private EnderecoService enderecoService;

	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Endereco> buscaEnderecoPorCep(@RequestBody Map<String, String> cep) {
		return ResponseEntity.of(enderecoService.buscarEnderecoPorCep(cep.get("cep")));
	}

}
