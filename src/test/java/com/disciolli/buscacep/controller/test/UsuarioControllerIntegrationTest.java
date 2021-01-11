package com.disciolli.buscacep.controller.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.disciolli.buscacep.service.UsuarioService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS) // Compartilhando a instancia para todos os testes da classe.
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioControllerIntegrationTest {

	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private String tokenAdmin;
	private String tokenUser;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UsuarioService usuarioService;

	private String usuario = "novousuario@teste.com.br";
	private String senha = "Novo@123";

	private String jsonTemplate = "{\n" + "\"usuario\": \"%s\",\n" + "\"senha\": \"%s\"\n" + "}";

	@BeforeAll
	void gerarTokenAutenticacao() {
		tokenAdmin = usuarioService.signin("admin@teste.com.br", "Admin@123");
		assertNotNull(tokenAdmin);

		tokenUser = usuarioService.signin("user@teste.com.br", "User@123");
		assertNotNull(tokenUser);
	}

	@Test
	@Order(1)
	void proibirCadastrarUsuarioNaoAutenticado() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/usuario/cadastrar").contentType(APPLICATION_JSON_UTF8)
				.content(String.format(jsonTemplate, usuario, senha))).andExpect(status().isForbidden());
	}

	@Test
	@Order(2)
	void cadastrarUsuarioAutenticadoAdmin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/usuario/cadastrar").header("Authorization", "Bearer " + tokenAdmin)
						.contentType(APPLICATION_JSON_UTF8).content(String.format(jsonTemplate, usuario, senha)))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	void proibirCadastrarUsuarioAutenticadoUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/usuario/cadastrar").header("Authorization", "Bearer " + tokenUser)
						.contentType(APPLICATION_JSON_UTF8).content(String.format(jsonTemplate, usuario, senha)))
				.andExpect(status().isForbidden());
	}

	@Test
	@Order(4)
	void barrarCadastrarUsuarioJaExistente() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/usuario/cadastrar").header("Authorization", "Bearer " + tokenAdmin)
						.contentType(APPLICATION_JSON_UTF8).content(String.format(jsonTemplate, usuario, senha)))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	@Order(5)
	void proibirExcluirUsuarioNaoAutenticado() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuario/" + usuario)).andExpect(status().isForbidden());
	}

	@Test
	@Order(6)
	void excluirUsuarioComAutenticacaoAdmin() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuario/" + usuario).header("Authorization",
				"Bearer " + tokenAdmin)).andExpect(status().isOk());
	}

	@Test
	@Order(7)
	void proibirExcluirUsuarioComAutenticacaoUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/usuario/" + usuario).header("Authorization", "Bearer " + tokenUser))
				.andExpect(status().isForbidden());
	}

	@Test
	@Order(8)
	void informarExcluirUsuarioComAutenticacaoUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuario/" + usuario).header("Authorization",
				"Bearer " + tokenAdmin)).andExpect(status().isNotFound());
	}

}
