package com.disciolli.buscacep.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disciolli.buscacep.dto.EnderecoDTO;
import com.disciolli.buscacep.model.Endereco;
import com.disciolli.buscacep.service.EnderecoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api")
@Api(value = "API Busca CEP")
public class EnderecoController {

	Logger logger = LoggerFactory.getLogger(EnderecoController.class);
	
	private EnderecoService enderecoService;

	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	@ApiOperation("Retorna um endereço, dado um CEP válido")
	@PostMapping(path = "/endereco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EnderecoDTO> buscaEnderecoPorCep(@Valid @RequestBody CepRequest cep) {

		logger.info("Consultando Cep {}", cep.getCep());
		
		Optional<Endereco> endereco = enderecoService.buscarEnderecoPorCep(cep.getCep());
		return endereco.isPresent() ? ResponseEntity.ok(endereco.get().toDTO()) : ResponseEntity.notFound().build();
	}

}
