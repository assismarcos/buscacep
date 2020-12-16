package com.disciolli.buscacep.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorMsg> validacaoDeCamposHandler(MethodArgumentNotValidException ex) {
		List<ErrorMsg> ret = new ArrayList<>();
		
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			ret.add(new ErrorMsg(fieldError.getDefaultMessage()));
		}
		
		return ret;
		
	}
	
}
