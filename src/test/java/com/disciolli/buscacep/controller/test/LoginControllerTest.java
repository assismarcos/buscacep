package com.disciolli.buscacep.controller.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.disciolli.buscacep.controller.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	private String usuario = "admin@teste.com.br";
	private String senha = "Admin@123";

	private String jsonTemplate = "{\n" + "\"usuario\": \"%s\",\n" + "\"senha\": \"%s\"\n" + "}";

	@Test
	void efetuarLogin() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
				.contentType(APPLICATION_JSON_UTF8).content(String.format(jsonTemplate, usuario, senha)))
				.andExpect(status().isOk()).andReturn();

		LoginResponse loginResponse = stringToLoginResponse(mvcResult.getResponse().getContentAsString());
		assertNotNull(loginResponse.getToken());
	}
	
	LoginResponse stringToLoginResponse(String response) {
		try {
			return objectMapper.readValue(response, LoginResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
