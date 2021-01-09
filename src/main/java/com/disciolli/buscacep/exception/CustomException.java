package com.disciolli.buscacep.exception;

import org.springframework.http.HttpStatus;
/**
 * Classe para tratar excecoes especificas para posterior tratamento.
 * @author Disciolli
 *
 */
public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String message;
  private final HttpStatus httpStatus;

  public CustomException(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
