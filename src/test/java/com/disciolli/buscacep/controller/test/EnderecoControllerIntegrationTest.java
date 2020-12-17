package com.disciolli.buscacep.controller.test;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.disciolli.buscacep.dto.EnderecoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class EnderecoControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void retornaEnderecoCepValido() throws Exception {

		String cep = "17526760";

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/endereco").contentType(APPLICATION_JSON_UTF8).content(asJsonString(cep)))
				.andReturn();

		EnderecoDTO enderecoDTO = stringToEnderecoDTO(mvcResult.getResponse().getContentAsString());

		Assertions.assertEquals(mvcResult.getResponse().getStatus(), 200);
		Assertions.assertEquals("Avenida Maria Fernandes Cavallari - de 3150/3151 a 3298/3299", enderecoDTO.getRua());

	}

	@Test
	public void retornaEnderecoCepValidoAproximado() throws Exception {
		String cep = "17526123";

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/endereco").contentType(APPLICATION_JSON_UTF8).content(asJsonString(cep)))
				.andReturn();

		Assertions.assertEquals(mvcResult.getResponse().getStatus(), 200);
		Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("SP"));
	}

	@Test
	public void retornaMensagemCepInvalidoSeCepNaoEstiverNoFormatoEsperado() throws Exception {
		String cep = "1752670";

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/endereco").contentType(APPLICATION_JSON_UTF8).content(asJsonString(cep)))
				.andReturn();

		Assertions.assertEquals(mvcResult.getResponse().getStatus(), 400);
		Assertions.assertTrue(mvcResult.getResponse().getContentAsString().contains("CEP inválido"));
	}

	@Test
	public void retornaStatus404QuandoEnderecoNaoFoiEncontrado() throws Exception {
		String cep = "99999999";

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/endereco").contentType(APPLICATION_JSON_UTF8).content(asJsonString(cep)))
				.andReturn();

		Assertions.assertEquals(mvcResult.getResponse().getStatus(), 404);
	}

	public String asJsonString(final Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public EnderecoDTO stringToEnderecoDTO(String response) {
		try {
			return objectMapper.readValue(response, EnderecoDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
