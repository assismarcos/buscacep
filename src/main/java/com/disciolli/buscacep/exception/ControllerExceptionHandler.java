package com.disciolli.buscacep.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@Bean
	public ErrorAttributes errorAttributes() {
		// Ocultando alguns campos do retorno da exception
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
				errorAttributes.remove("exception");
				errorAttributes.remove("trace");
				return errorAttributes;
			}
		};
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorMsg> validacaoDeCamposHandler(MethodArgumentNotValidException ex) {
		List<ErrorMsg> ret = new ArrayList<>();

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			ret.add(new ErrorMsg(fieldError.getDefaultMessage()));
		}

		return ret;

	}

	@ExceptionHandler(CustomException.class)
	public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
		res.sendError(ex.getHttpStatus().value(), ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.FORBIDDEN.value(), "Acesso negado");
	}

	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.BAD_REQUEST.value(), "Ops...");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public void handleAuthenticationException(HttpServletResponse res, AuthenticationException ex) throws IOException {
		res.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public void handleBadCredentialsException(HttpServletResponse res, BadCredentialsException ex) throws IOException {
		res.sendError(HttpStatus.UNAUTHORIZED.value(), "Usuário inexistente ou senha inválida");
	}

}
