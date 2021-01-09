package com.disciolli.buscacep.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disciolli.buscacep.service.UsuarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/usuario")
public class UsuarioController {

	Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	private UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@ApiOperation("Incluir um usuário")
	@PostMapping(path = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void signup(@RequestBody UsuarioRequest usuario) {
		logger.info("Incluindo usuario [{}]", usuario.getUsuario());
		usuarioService.signup(usuario.getUsuario(), usuario.getSenha());
	}

	@ApiOperation("Excluir um usuário")
	@DeleteMapping(value = "/{nome}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(@PathVariable String nome) {
		logger.info("Excluindo usuario [{}]", nome);
		usuarioService.delete(nome);
	}

}
