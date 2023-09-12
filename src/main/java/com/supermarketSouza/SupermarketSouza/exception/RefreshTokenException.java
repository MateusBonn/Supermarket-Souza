package com.supermarketSouza.SupermarketSouza.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class RefreshTokenException extends BadCredentialsException {

  public RefreshTokenException(String msg) {
    super(msg);
  }
}
