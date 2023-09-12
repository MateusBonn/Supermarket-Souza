package com.supermarketSouza.SupermarketSouza.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class TokenValidationException extends JWTVerificationException {

  public TokenValidationException(String message) {
    super(message);
  }
}
