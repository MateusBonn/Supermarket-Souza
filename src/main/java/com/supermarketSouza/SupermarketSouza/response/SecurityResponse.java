package com.supermarketSouza.SupermarketSouza.response;

import com.supermarketSouza.SupermarketSouza.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class SecurityResponse {

  public LoginResponse login;

  public AutenticatedResponse token;
}
