package com.disciolli.buscacep.service.test;

import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.disciolli.buscacep.model.Endereco;
import com.disciolli.buscacep.repository.EnderecoRepository;
import com.disciolli.buscacep.service.EnderecoService;

@SpringBootTest
class EnderecoServiceTest {

	@Mock
	private EnderecoRepository enderecoRepository;

	@Autowired
	private EnderecoService enderecoService;

	@Test
	void testarCepExistente() {
		String cep = "17526000";

		when(enderecoRepository.findById(cep)).thenReturn(
				Optional.of(new Endereco(cep, "Avenida Manoel Pereira", "Jardim Morumbi", "Marília", "SP")));

		Optional<Endereco> endereco = enderecoService.buscarEnderecoPorCep(cep);

		Assertions.assertEquals("Avenida Manoel Pereira", endereco.get().getLogradouro());
	}

	@Test
	void testarCepInexistente() {
		String cep = "10000000";

		when(enderecoRepository.findById(cep)).thenReturn(null);

		Optional<Endereco> enderecoMock = enderecoService.buscarEnderecoPorCep(cep);
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			enderecoMock.get();
		});
	}

	@Test
	void testarCepAprox() {

		String cepInexistente = "17525123";
		String cepAprox = "17520000";

		when(enderecoRepository.findById(cepAprox)).thenReturn(Optional
				.of(new Endereco(cepAprox, "Rua Itália - de 292/293 ao fim", "Jardim Vista Alegre", "Marília", "SP")));

		Optional<Endereco> endereco = enderecoService.buscarEnderecoPorCep(cepInexistente);

		Assertions.assertEquals(true, endereco.isPresent());
		Assertions.assertEquals("Rua Itália - de 292/293 ao fim", endereco.get().getLogradouro());
	}

}
