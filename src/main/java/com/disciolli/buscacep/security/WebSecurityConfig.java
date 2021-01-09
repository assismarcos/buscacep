package com.disciolli.buscacep.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe para configurar Spring Security definindo a seguranca de acesso aos
 * recursos.
 * 
 * @author Disciolli
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private JwtTokenFilter jwtTokenFilter;
	
	public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {
		this.jwtTokenFilter = jwtTokenFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Habilita CORS para permitir requisicao de aplicacoes externas e desabilita CSRF.
		http.cors().and().csrf().disable();

		// Nao sera necessario criar sessao pois o token eh enviado na requisicao http.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()//
				// Permite somente acessar o login sem estar autenticado.
				.antMatchers("/api/login").permitAll()//
				// Para qualquer outro acesso precisa estar autenticado.
				.anyRequest().authenticated();

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Permite acessar Swagger sem estar autenticado.
		web.ignoring().antMatchers("/v2/api-docs")//
				.antMatchers("/v3/api-docs")//
				.antMatchers("/swagger-resources/**")//
				.antMatchers("/swagger-ui.html")//
				.antMatchers("/swagger-ui/**")//

				// Apenas para teste permite acessar o console do H2 Database sem autenticacao.
				.and().ignoring().antMatchers("/h2-console/**");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
