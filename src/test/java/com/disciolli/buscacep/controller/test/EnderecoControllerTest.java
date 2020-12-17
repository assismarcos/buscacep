package com.disciolli.buscacep.controller.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.disciolli.buscacep.controller.CepRequest;
import com.disciolli.buscacep.controller.EnderecoController;
import com.disciolli.buscacep.dto.EnderecoDTO;
import com.disciolli.buscacep.model.Endereco;
import com.disciolli.buscacep.repository.EnderecoRepository;
import com.disciolli.buscacep.service.EnderecoService;

@SpringBootTest
class EnderecoControllerTest {

	@InjectMocks
	private EnderecoController enderecoController;

	@Mock
	private EnderecoService enderecoService;

	@Mock
	private EnderecoRepository enderecoRepository;

	@BeforeEach
	void init() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	void retornaEnderecoCepEncontrado() {

		String cep = "17526000";
		Endereco endereco = new Endereco();
		endereco.setCep(cep);
		endereco.setLogradouro("Avenida Manoel Pereira");

		when(enderecoService.buscarEnderecoPorCep(cep)).thenReturn(Optional.of(endereco));
		when(enderecoRepository.findById(cep)).thenReturn(Optional.of(endereco));

		ResponseEntity<EnderecoDTO> enderecoRetornado = enderecoController.buscaEnderecoPorCep(new CepRequest(cep));

		assertThat(enderecoRetornado.getStatusCode().is2xxSuccessful()).isTrue();
		Assertions.assertEquals("Avenida Manoel Pereira", enderecoRetornado.getBody().getRua());
	}

	@Test
	void retorna404CepValidoInexistente() {

		String cep = "10203040";

		when(enderecoService.buscarEnderecoPorCep(cep)).thenReturn(Optional.ofNullable(null));
		when(enderecoRepository.findById(cep)).thenReturn(null);

		ResponseEntity<EnderecoDTO> enderecoRetornado = enderecoController.buscaEnderecoPorCep(new CepRequest(cep));

		assertEquals(404, enderecoRetornado.getStatusCode().value());

	}

}
