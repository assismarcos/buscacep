package com.disciolli.buscacep.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.disciolli.buscacep.dto.EnderecoDTO;
import com.disciolli.buscacep.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS) // Compartilhando a instancia para todos os testes da classe.
class EnderecoControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UsuarioService usuarioService;

	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private String token;

	@Test
	void naoPermitirConsultarSemAutenticar() throws Exception {
		String cep = "{\"cep\" : \"17526760\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/endereco").contentType(APPLICATION_JSON_UTF8).content(cep))
				.andExpect(status().isForbidden());
	}

	@BeforeAll
	void gerarTokenAutenticacao() {
		token = usuarioService.signin("admin@teste.com.br", "Admin@123");
		assertNotNull(token);
	}

	@Test
	void retornaEnderecoCepValido() throws Exception {
		String cep = "{\"cep\" : \"17526760\"}";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/endereco")
				.header("Authorization", "Bearer " + token).contentType(APPLICATION_JSON_UTF8).content(cep))
				.andExpect(status().isOk()).andReturn();

		EnderecoDTO enderecoDTO = stringToEnderecoDTO(mvcResult.getResponse().getContentAsString());

		assertEquals("Avenida Maria Fernandes Cavallari - de 3150/3151 a 3298/3299", enderecoDTO.getRua());
	}

	@Test
	void retornaEnderecoCepValidoAproximado() throws Exception {
		String cep = "{\"cep\" : \"17526123\"}";

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/endereco").header("Authorization", "Bearer " + token)
						.contentType(APPLICATION_JSON_UTF8).content(cep))
				.andExpect(status().isOk()).andReturn();

		assertTrue(mvcResult.getResponse().getContentAsString().contains("SP"));
	}

	@Test
	void retornaMensagemCepInvalidoSeCepNaoEstiverNoFormatoEsperado() throws Exception {
		String cep = "{\"cep\" : \"1752670\"}";

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/endereco").header("Authorization", "Bearer " + token)
						.contentType(APPLICATION_JSON_UTF8).content(cep))
				.andExpect(status().isBadRequest()).andReturn();

		assertTrue(mvcResult.getResponse().getContentAsString().contains("CEP inválido"));
	}

	@Test
	void retornaStatus404QuandoEnderecoNaoFoiEncontrado() throws Exception {
		String cep = "{\"cep\" : \"99999999\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/endereco").header("Authorization", "Bearer " + token)
				.contentType(APPLICATION_JSON_UTF8).content(cep)).andExpect(status().isNotFound())
				.andReturn();
	}

	EnderecoDTO stringToEnderecoDTO(String response) {
		try {
			return objectMapper.readValue(response, EnderecoDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
