package com.disciolli.buscacep.service.test;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.disciolli.buscacep.repository.UsuarioRepository;

@SpringBootTest
public class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;
	
}
