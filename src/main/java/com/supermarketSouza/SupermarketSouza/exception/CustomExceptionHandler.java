package com.supermarketSouza.SupermarketSouza.exception;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.supermarketSouza.SupermarketSouza.response.ErrorMessage;
import jakarta.persistence.NoResultException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(new Date(), e.getCause(), "Erro ao autenticar login"));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(new Date(), e.getCause(), "Login ou senha inválidos"));
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ErrorMessage> handleTokenValidationException(TokenExpiredException  e) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(new Date(), e.getCause(), "Token inválido ou expirado"));
  }

  @ExceptionHandler(NoResultException.class)
  public ResponseEntity<ErrorMessage> handleResultException(NoResultException  e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(new Date(), e.getCause(), "Produto não encontrado no banco de dados"));
  }
}

