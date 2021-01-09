package com.disciolli.buscacep.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.disciolli.buscacep.exception.CustomException;
import com.disciolli.buscacep.model.Funcao;
import com.disciolli.buscacep.service.UsuarioDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * Classe utilitaria para criar e validar o Token JWT.
 * @author Disciolli
 *
 */
@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expirems:3600000}") // 1h
	private long validityInMilliseconds;

	@Autowired
	private UsuarioDetailsService usuarioDetails;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public String createToken(String username, Funcao role) {

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("auth", new SimpleGrantedAuthority(role.name()));

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
	}

	public Authentication getAuthentication(String token) {
		String user = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		UserDetails userDetails = usuarioDetails.loadUserByUsername(user);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			throw new CustomException("Token JWT invalido ou expirado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
