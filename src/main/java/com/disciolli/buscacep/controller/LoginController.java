package com.disciolli.buscacep.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disciolli.buscacep.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/api")
@Api(value = "API para validar acesso")
public class LoginController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	private UsuarioService usuarioService;

	public LoginController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Autenticar")
	public LoginResponse login(@ApiParam("Credencial") @Valid @RequestBody UsuarioRequest credencial) {
		logger.info("Autenticando usuario [{}]", credencial.getUsuario());
		return new LoginResponse(usuarioService.signin(credencial.getUsuario(), credencial.getSenha()));
	}

}
