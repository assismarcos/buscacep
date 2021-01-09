package com.disciolli.buscacep.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.disciolli.buscacep.exception.CustomException;
import com.disciolli.buscacep.model.Funcao;
import com.disciolli.buscacep.model.Usuario;
import com.disciolli.buscacep.repository.UsuarioRepository;
import com.disciolli.buscacep.security.JwtTokenProvider;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public UsuarioService(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.usuarioRepository = usuarioRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String signin(String nome, String senha) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nome, senha));
		return jwtTokenProvider.createToken(nome, usuarioRepository.findByNome(nome).getFuncao());
	}

	public void signup(String nome, String senha) {
		if (!usuarioRepository.existsByNome(nome)) {
			Usuario usuario = new Usuario(nome, passwordEncoder.encode(senha), Funcao.ROLE_USER);
			usuarioRepository.save(usuario);
		} else {
			throw new CustomException("O usuario ja existe", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public void delete(String nome) {
		if (usuarioRepository.existsByNome(nome)) {
			usuarioRepository.deleteByNome(nome);
		} else {
			throw new CustomException("Usuario nao encontrado", HttpStatus.NOT_FOUND);
		}
	}

}
